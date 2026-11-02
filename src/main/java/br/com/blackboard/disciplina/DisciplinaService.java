package br.com.blackboard.disciplina;

import br.com.blackboard.professor.Professor;
import br.com.blackboard.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public DisciplinaResponse create(DisciplinaRequest request) {
        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        if(disciplinaRepository.existsByCodigo(request.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código pertence a outra disciplina cadastrada");
        }

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo(request.getCodigo());
        disciplina.setNome(request.getNome());
        disciplina.setCargaHoraria(request.getCargaHoraria());
        disciplina.setProfessor(professor);

        return new DisciplinaResponse(disciplinaRepository.save(disciplina));
    }

    public List<DisciplinaResponse> findAll() {
        return disciplinaRepository.findAll().stream()
                .map(DisciplinaResponse::new)
                .toList();
    }

    public DisciplinaResponse findById(int id) {
        return disciplinaRepository.findById(id)
                .map(DisciplinaResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
    }
}
