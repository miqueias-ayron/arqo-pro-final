package br.com.blackboard.professor;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessorResponse create(@Valid @RequestBody ProfessorRequest request) {
        return professorService.create(request);
    }

    @GetMapping
    public List<ProfessorResponse> findAll() {
        return professorService.findAll();
    }

    @GetMapping("/{id}")
    public ProfessorResponse findById(@PathVariable int id) {
        return professorService.findById(id);
    }
}
