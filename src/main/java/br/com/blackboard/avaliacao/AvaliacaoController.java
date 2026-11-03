package br.com.blackboard.avaliacao;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse create(@Valid @RequestBody AvaliacaoRequest request, @AuthenticationPrincipal UserDetails usuario) {
        return avaliacaoService.create(request, usuario);
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
    public AvaliacaoResponse update(@PathVariable int id, @Valid @RequestBody AvaliacaoRequest request,
                                    @AuthenticationPrincipal UserDetails usuario) {
        return avaliacaoService.update(id, request, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal UserDetails usuario) {
        avaliacaoService.delete(id, usuario);
    }
}