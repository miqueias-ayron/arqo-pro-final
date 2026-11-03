package br.com.blackboard.disciplina;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping("/disciplinas")
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplinaResponse create(@Valid @RequestBody DisciplinaRequest request)  {
        return disciplinaService.create(request);
    }

    @GetMapping("/disciplinas")
    public List<DisciplinaResponse> findAll() {
        return disciplinaService.findAll();
    }

    @GetMapping("/disciplinas/{id}")
    public DisciplinaResponse findById(@PathVariable int id) {
        return disciplinaService.findById(id);
    }


    @PutMapping("/disciplinas/{id}")
    public DisciplinaResponse update(@PathVariable int id, @Valid @RequestBody DisciplinaRequest request) {
        return disciplinaService.update(id, request);
    }
}
