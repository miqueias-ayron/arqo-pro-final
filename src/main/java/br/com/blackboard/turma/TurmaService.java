package br.com.blackboard.turma;

import br.com.blackboard.disciplina.Disciplina;
import br.com.blackboard.disciplina.DisciplinaRepository;
import br.com.blackboard.sala.Sala;
import br.com.blackboard.sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private SalaRepository salaRepository;

    public TurmaResponse create(TurmaRequest request) {
        String codigo = request.getCodigo();
        String semestre = request.getSemestre();
        String horario = request.getHorario();

        Disciplina disciplina = disciplinaRepository.findById(request.getIdDisciplina())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));

        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada"));

        if (turmaRepository.existsByCodigoAndDisciplinaIdAndSemestre(codigo, disciplina.getId(), semestre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe uma turma com esse código nessa disciplina e semestre");
        }

        if (turmaRepository.existsBySalaIdAndSemestreAndHorario(sala.getId(), semestre, horario)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Sala já ocupada por outra turma nesse horário");
        }

        Turma turma = new Turma();
        turma.setCodigo(codigo);
        turma.setSemestre(semestre);
        turma.setHorario(horario);
        turma.setDisciplina(disciplina);
        turma.setSala(sala);

        return new TurmaResponse(turmaRepository.save(turma));
    }

    public List<TurmaResponse> findAll() {
        return turmaRepository.findAll().stream()
                .map(TurmaResponse::new)
                .toList();
    }

    public TurmaResponse findById(int id) {
        return turmaRepository.findById(id)
                .map(TurmaResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
    }

    @Transactional
    public TurmaResponse update(int id, TurmaRequest request) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Disciplina disciplina = disciplinaRepository.findById(request.getIdDisciplina())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));

        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada"));

        if (turmaRepository.existsByCodigoAndDisciplinaIdAndSemestreAndIdNot(
                request.getCodigo(), disciplina.getId(), request.getSemestre(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe uma turma com esse código nessa disciplina e semestre");
        }

        if (turmaRepository.existsBySalaIdAndSemestreAndHorarioAndIdNot(
                sala.getId(), request.getSemestre(), request.getHorario(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Sala já ocupada por outra turma nesse horário");
        }

        turma.setCodigo(request.getCodigo());
        turma.setSemestre(request.getSemestre());
        turma.setHorario(request.getHorario());
        turma.setDisciplina(disciplina);
        turma.setSala(sala);
        return new TurmaResponse(turmaRepository.save(turma));
    }
}
