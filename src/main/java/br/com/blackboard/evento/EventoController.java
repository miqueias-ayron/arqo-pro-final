package br.com.blackboard.evento;

import br.com.blackboard.aluno.AlunoResumoResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventos")
    public List<EventoResponse> findAll() {
        return eventoService.findAll();
    }

    @PostMapping("/eventos")
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse create(@Valid @RequestBody EventoRequest request) {
        return eventoService.create(request);
    }

    @GetMapping("/eventos/{id}")
    public EventoResponse findById(@PathVariable int id) {
        return eventoService.findById(id);
    }

    @PutMapping("/eventos/{id}")
    public EventoResponse update(@PathVariable int id, @Valid @RequestBody EventoRequest request) {
        return eventoService.update(id, request);
    }

    @GetMapping("/eventos/{id}/inscritos")
    public List<AlunoResumoResponse> inscritos(@PathVariable int id) {
        return eventoService.inscritos(id);
    }
}
