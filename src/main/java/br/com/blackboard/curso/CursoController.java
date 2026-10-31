package br.com.blackboard.curso;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/cursos")
    public List<CursoResponse> getAll() {
        return cursoService.findAll();
    }

    @PostMapping("/cursos")
    public CursoResponse create(@Valid @RequestBody CursoRequest request) {
        return cursoService.create(request);
    }

    @GetMapping("/cursos/{id}")
    public CursoResponse getById(@PathVariable int id) {
        return cursoService.getById(id);
    }
}
