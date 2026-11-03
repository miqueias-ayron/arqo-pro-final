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
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private NotaService notaService;

    @Transactional
    public MatriculaResponse create(MatriculaRequest request) {
        Aluno aluno = buscarAluno(request.getIdAluno());
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

    @Transactional(readOnly = true)
    public List<MatriculaResponse> findAll() {
        return matriculaRepository.findAll().stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaResponse findById(int id, UserDetails usuario) {
        Matricula matricula = buscar(id);
        PermissaoMatricula.validarConsulta(matricula.getAluno(), usuario);
        return new MatriculaResponse(matricula);
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponse> findByAluno(int idAluno, UserDetails usuario) {
        PermissaoMatricula.validarConsulta(buscarAluno(idAluno), usuario);
        return matriculaRepository.findByAlunoId(idAluno).stream()
                .map(MatriculaResponse::new)
                .toList();
    }

    @Transactional
    public MatriculaResponse atualizarFrequencia(int id, FrequenciaRequest request) {
        Matricula matricula = buscar(id);

        matricula.setFrequencia(request.getFrequencia());
        notaService.recalcularStatus(matricula);

        return new MatriculaResponse(matricula);
    }

    private Matricula buscar(int id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
    }

    private Aluno buscarAluno(int id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
    }
}
