package br.com.blackboard.nota;

import br.com.blackboard.avaliacao.Avaliacao;
import br.com.blackboard.avaliacao.AvaliacaoRepository;
import br.com.blackboard.common.Permissoes;
import br.com.blackboard.matricula.Matricula;
import br.com.blackboard.matricula.MatriculaRepository;
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
    public NotaResponse lancar(NotaRequest request, UserDetails usuario) {
        Matricula matricula = buscarMatricula(request.getIdMatricula());
        Permissoes.exigirMasterOuProfessorDaTurma(usuario, matricula.getTurma());
        Avaliacao avaliacao = avaliacaoRepository.findById(request.getIdAvaliacao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        if (avaliacao.getTurma().getId() != matricula.getTurma().getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A avaliação não é da turma da matrícula");
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

        // Master vê qualquer boletim; professor só das turmas em que dá aula; aluno só o próprio
        boolean permitido = Permissoes.temPerfil(usuario, "MASTER")
                || (Permissoes.temPerfil(usuario, "PROFESSOR") && Permissoes.ehProfessorDaTurma(usuario, matricula.getTurma()))
                || matricula.getAluno().getUsuario().getEmail().equals(usuario.getUsername());
        if (!permitido) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para ver este boletim");
        }

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
            matricula.setMediaFinal(null);
        } else {
            BigDecimal media = CalculadoraMedia.calcularMedia(notas);
            matricula.setMediaFinal(media);
            matricula.setStatus(CalculadoraMedia.definirStatus(media, matricula.getFrequencia()));
        }

        matriculaRepository.save(matricula);
    }

    private Matricula buscarMatricula(int id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }
}
