package br.com.blackboard.organizacaoestudantil;

import br.com.blackboard.aluno.AlunoResumoResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizacaoEstudantilController {

    @Autowired
    private OrganizacaoEstudantilService organizacaoEstudantilService;

    @PostMapping("/organizacoes-estudantis")
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizacaoEstudantil create(@Valid @RequestBody OrganizacaoEstudantil organizacao) {
        return organizacaoEstudantilService.create(organizacao);
    }

    @GetMapping("/organizacoes-estudantis")
    public List<OrganizacaoEstudantil> findAll() {
        return organizacaoEstudantilService.findAll();
    }

    @GetMapping("/organizacoes-estudantis/{id}")
    public OrganizacaoEstudantil findById(@PathVariable int id) {
        return organizacaoEstudantilService.findById(id);
    }

    @PutMapping("/organizacoes-estudantis/{id}")
    public OrganizacaoEstudantil update(@PathVariable int id, @Valid @RequestBody OrganizacaoEstudantil organizacao) {
        return organizacaoEstudantilService.update(id, organizacao);
    }

    @GetMapping("/organizacoes-estudantis/{id}/membros")
    public List<AlunoResumoResponse> membros(@PathVariable int id) {
        return organizacaoEstudantilService.membros(id);
    }
}
