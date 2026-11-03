package br.com.blackboard.matricula;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping("/matriculas")
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse create(@Valid @RequestBody MatriculaRequest request) {
        return matriculaService.create(request);
    }

    @GetMapping("/matriculas")
    public List<MatriculaResponse> findAll() {
        return matriculaService.findAll();
    }

    @GetMapping("/matriculas/{id}")
    public MatriculaResponse findById(@PathVariable int id, @AuthenticationPrincipal UserDetails usuario) {
        return matriculaService.findById(id, usuario);
    }

    @GetMapping("/alunos/{idAluno}/matriculas")
    public List<MatriculaResponse> findByAluno(@PathVariable int idAluno, @AuthenticationPrincipal UserDetails usuario) {
        return matriculaService.findByAluno(idAluno, usuario);
    }

    @PatchMapping("/matriculas/{id}/frequencia")
    public MatriculaResponse atualizarFrequencia(@PathVariable int id, @Valid @RequestBody FrequenciaRequest request) {
        return matriculaService.atualizarFrequencia(id, request);
    }
}
