package br.com.blackboard.sala;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping("/salas")
    @ResponseStatus(HttpStatus.CREATED)
    public Sala create(@Valid @RequestBody Sala sala) {
        return salaService.create(sala);
    }

    @GetMapping("/salas")
    public List<Sala> findAll() {
        return salaService.findAll();
    }

    @GetMapping("/salas/{id}")
    public Sala findById(@PathVariable int id) {
        return salaService.findById(id);
    }
}
