package br.com.blackboard.matricula;

import br.com.blackboard.aluno.Aluno;
import br.com.blackboard.aluno.AlunoRepository;
import br.com.blackboard.common.Permissoes;
import br.com.blackboard.nota.NotaService;
import br.com.blackboard.turma.Turma;
import br.com.blackboard.turma.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private NotaService notaService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public MatriculaResponse create(MatriculaRequest request) {
        Aluno aluno = alunoRepository.findById(request.getIdAluno())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        Turma turma = turmaRepository.findById(request.getIdTurma())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        if (matriculaRepository.existsByAlunoIdAndTurmaId(aluno.getId(), turma.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já matriculado nesta turma");
        }

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        return new MatriculaResponse(matriculaRepository.save(matricula));
    }

    @Transactional
    public MatriculaResponse atualizarFrequencia(int id, FrequenciaRequest request) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        matricula.setFrequencia(request.getFrequencia());
        notaService.recalcularStatus(matricula);

        return new MatriculaResponse(matricula);
    }

    // Master vê todas; professor só as das turmas em que dá aula; aluno só as próprias
    @Transactional(readOnly = true)
    public List<MatriculaResponse> findByAluno(int idAluno, UserDetails usuario) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        boolean ehMaster = Permissoes.temPerfil(usuario, "MASTER");
        boolean ehProfessor = Permissoes.temPerfil(usuario, "PROFESSOR");
        if (!ehMaster && !ehProfessor && !aluno.getUsuario().getEmail().equals(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para ver as matrículas deste aluno");
        }

        return matriculaRepository.findByAlunoIdOrderById(idAluno).stream()
                .filter(matricula -> !ehProfessor || Permissoes.ehProfessorDaTurma(usuario, matricula.getTurma()))
                .map(MatriculaResponse::new)
                .toList();
    }

    // Master vê qualquer turma; professor só as turmas em que dá aula
    @Transactional(readOnly = true)
    public List<MatriculaResponse> findByTurma(int idTurma, UserDetails usuario) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        if (!Permissoes.temPerfil(usuario, "MASTER") && !Permissoes.ehProfessorDaTurma(usuario, turma)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para ver as matrículas desta turma");
        }

        return matriculaRepository.findByTurmaIdOrderByAlunoNome(idTurma).stream()
                .map(MatriculaResponse::new)
                .toList();
    }
}
