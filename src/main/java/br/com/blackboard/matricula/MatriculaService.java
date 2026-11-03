package br.com.blackboard.matricula;

import br.com.blackboard.aluno.Aluno;
import br.com.blackboard.aluno.AlunoRepository;
import br.com.blackboard.nota.NotaService;
import br.com.blackboard.turma.Turma;
import br.com.blackboard.turma.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
}
