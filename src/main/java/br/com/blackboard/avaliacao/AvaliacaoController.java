package br.com.blackboard.avaliacao;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse create(@Valid @RequestBody AvaliacaoRequest request) {
        return avaliacaoService.create(request);
    }

    @GetMapping
    public List<AvaliacaoResponse> findAll() {
        return avaliacaoService.findAll();
    }

    @GetMapping("/{id}")
    public AvaliacaoResponse findById(@PathVariable int id) {
        return avaliacaoService.findById(id);
    }

    @PutMapping("/{id}")
    public AvaliacaoResponse update(@PathVariable int id, @Valid @RequestBody
    AvaliacaoRequest request) {
        return avaliacaoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        avaliacaoService.delete(id);
    }
}