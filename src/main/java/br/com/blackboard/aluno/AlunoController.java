package br.com.blackboard.aluno;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse create(@Valid @RequestBody AlunoRequest request) {
        return alunoService.create(request);
    }

    @GetMapping
    public List<AlunoResponse> findAll() {
        return alunoService.findAll();
    }

    @GetMapping("/{id}")
    public AlunoResponse findById(@PathVariable int id) {
        return alunoService.findById(id);
    }

    @PostMapping("/{id}/organizacoes/{idOrganizacao}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse addOrganizacao(@PathVariable int id, @PathVariable int idOrganizacao,
                                        @AuthenticationPrincipal UserDetails usuario) {
        return alunoService.addOrganizacao(id, idOrganizacao, usuario);
    }

    @PostMapping("/{id}/eventos/{idEvento}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse addEvento(@PathVariable int id, @PathVariable int idEvento,
                                   @AuthenticationPrincipal UserDetails usuario) {
        return alunoService.addEvento(id, idEvento, usuario);
    }
}
