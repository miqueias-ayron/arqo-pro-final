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

    @PatchMapping("/matriculas/{id}/frequencia")
    public MatriculaResponse atualizarFrequencia(@PathVariable int id, @Valid @RequestBody FrequenciaRequest request,
                                                 @AuthenticationPrincipal UserDetails usuario) {
        return matriculaService.atualizarFrequencia(id, request, usuario);
    }

    @GetMapping("/alunos/{idAluno}/matriculas")
    public List<MatriculaResponse> findByAluno(@PathVariable int idAluno, @AuthenticationPrincipal UserDetails usuario) {
        return matriculaService.findByAluno(idAluno, usuario);
    }

    @GetMapping("/turmas/{idTurma}/matriculas")
    public List<MatriculaResponse> findByTurma(@PathVariable int idTurma, @AuthenticationPrincipal UserDetails usuario) {
        return matriculaService.findByTurma(idTurma, usuario);
    }
}
