package br.com.blackboard.turma;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping("/turmas")
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaResponse create(@Valid @RequestBody TurmaRequest request) {
        return turmaService.create(request);
    }

    @GetMapping("/turmas")
    public List<TurmaResponse> findAll() {
        return turmaService.findAll();
    }

    @GetMapping("/turmas/{id}")
    public TurmaResponse findById(@PathVariable  int id){
        return turmaService.findById(id);
    }

}
