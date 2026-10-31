package br.com.blackboard.curso;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/cursos")
    public List<CursoResponse> findAll() {
        return cursoService.findAll();
    }

    @PostMapping("/cursos")
    @ResponseStatus(HttpStatus.CREATED)
    public CursoResponse create(@Valid @RequestBody CursoRequest request) {
        return cursoService.create(request);
    }

    @GetMapping("/cursos/{id}")
    public CursoResponse findById(@PathVariable int id) {
        return cursoService.findById(id);
    }
}
