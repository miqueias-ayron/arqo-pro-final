package br.com.blackboard.curso;

import br.com.blackboard.professor.Professor;
import br.com.blackboard.professor.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public CursoResponse create(CursoRequest request) {
        Professor coordenador = professorRepository.findById(request.getIdCoordenador())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        if (cursoRepository.existsByCoordenadorId(coordenador.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Professor já é coordenador de outro curso");
        }

        Curso curso = new Curso();
        curso.setNome(request.getNome());
        curso.setCoordenador(coordenador);
        curso.setDuracaoSemestres(request.getDuracaoSemestres());

        return new CursoResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> findAll() {
        return cursoRepository.findAll().stream()
                .map(CursoResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoResponse getById(int id) {
        return cursoRepository.findById(id)
                .map(CursoResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

}
