package br.com.blackboard.nota;

import br.com.blackboard.avaliacao.Avaliacao;
import br.com.blackboard.avaliacao.AvaliacaoRepository;
import br.com.blackboard.matricula.Matricula;
import br.com.blackboard.matricula.MatriculaRepository;
import br.com.blackboard.matricula.PermissaoMatricula;
import br.com.blackboard.matricula.StatusMatricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public NotaResponse lancar(NotaRequest request) {
        Matricula matricula = buscarMatricula(request.getIdMatricula());
        Avaliacao avaliacao = avaliacaoRepository.findById(request.getIdAvaliacao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        if (avaliacao.getTurma().getId() != matricula.getTurma().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A avaliação não pertence à turma da matrícula");
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByTurmaId(matricula.getTurma().getId());
        if (somarPesos(avaliacoes).compareTo(BigDecimal.ONE) != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A soma dos pesos das avaliações da turma precisa ser 1 para lançar notas");
        }

        NotaId id = new NotaId(matricula.getId(), avaliacao.getId());
        Nota nota = notaRepository.findById(id).orElseGet(Nota::new);
        nota.setId(id);
        nota.setMatricula(matricula);
        nota.setAvaliacao(avaliacao);
        nota.setValor(request.getValor());
        nota = notaRepository.saveAndFlush(nota);

        recalcularStatus(matricula);
        return new NotaResponse(nota);
    }

    @Transactional(readOnly = true)
    public BoletimResponse boletim(int idMatricula, UserDetails usuario) {
        Matricula matricula = buscarMatricula(idMatricula);
        PermissaoMatricula.validarConsulta(matricula.getAluno(), usuario);
        return new BoletimResponse(matricula, notaRepository.findByMatriculaId(idMatricula));
    }

    // Só define aprovado ou reprovado quando a turma fecha os pesos em 1 e todas as avaliações têm nota
    @Transactional
    public void recalcularStatus(Matricula matricula) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByTurmaId(matricula.getTurma().getId());
        List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());

        boolean completa = somarPesos(avaliacoes).compareTo(BigDecimal.ONE) == 0
                && notas.size() == avaliacoes.size();

        if (completa) {
            BigDecimal media = CalculadoraMedia.calcularMedia(notas);
            matricula.setStatus(CalculadoraMedia.definirStatus(media, matricula.getFrequencia()));
        } else {
            matricula.setStatus(StatusMatricula.CURSANDO);
        }

        matriculaRepository.save(matricula);
    }

    private BigDecimal somarPesos(List<Avaliacao> avaliacoes) {
        return avaliacoes.stream()
                .map(Avaliacao::getPeso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Matricula buscarMatricula(int id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }
}
