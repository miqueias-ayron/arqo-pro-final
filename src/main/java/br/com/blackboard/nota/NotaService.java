package br.com.blackboard.nota;

import br.com.blackboard.avaliacao.Avaliacao;
import br.com.blackboard.avaliacao.AvaliacaoRepository;
import br.com.blackboard.matricula.Matricula;
import br.com.blackboard.matricula.MatriculaRepository;
import br.com.blackboard.matricula.StatusMatricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public BoletimResponse boletim(int idMatricula) {
        Matricula matricula = buscarMatricula(idMatricula);
        return new BoletimResponse(matricula, notaRepository.findByMatriculaId(idMatricula));
    }

    // Só define aprovado ou reprovado quando todas as avaliações (soma dos pesos = 1) têm nota
    @Transactional
    public void recalcularStatus(Matricula matricula) {
        List<Nota> notas = notaRepository.findByMatriculaId(matricula.getId());

        BigDecimal somaPesos = notas.stream()
                .map(nota -> nota.getAvaliacao().getPeso())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (somaPesos.compareTo(BigDecimal.ONE) < 0) {
            matricula.setStatus(StatusMatricula.CURSANDO);
        } else {
            BigDecimal media = CalculadoraMedia.calcularMedia(notas);
            matricula.setStatus(CalculadoraMedia.definirStatus(media, matricula.getFrequencia()));
        }

        matriculaRepository.save(matricula);
    }

    private Matricula buscarMatricula(int id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }
}
