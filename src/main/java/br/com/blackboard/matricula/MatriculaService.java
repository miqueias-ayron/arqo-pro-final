package br.com.blackboard.matricula;

import br.com.blackboard.aluno.Aluno;
import br.com.blackboard.aluno.AlunoRepository;
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

    // Aluno só vê as próprias matrículas; master e professor veem as de qualquer aluno
    @Transactional(readOnly = true)
    public List<MatriculaResponse> findByAluno(int idAluno, UserDetails usuario) {
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        boolean ehAluno = usuario.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ALUNO".equals(authority.getAuthority()));
        if (ehAluno && !aluno.getUsuario().getEmail().equals(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para ver as matrículas deste aluno");
        }

        return matriculaRepository.findByAlunoIdOrderById(idAluno).stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponse> findByTurma(int idTurma) {
        if (!turmaRepository.existsById(idTurma)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada");
        }

        return matriculaRepository.findByTurmaIdOrderByAlunoNome(idTurma).stream()
                .map(MatriculaResponse::new)
                .toList();
    }
}
