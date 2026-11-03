package br.com.blackboard.matricula;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse create(@Valid @RequestBody MatriculaRequest request) {
        return matriculaService.create(request);
    }

    @PatchMapping("/{id}/frequencia")
    public MatriculaResponse atualizarFrequencia(@PathVariable int id, @Valid @RequestBody FrequenciaRequest request) {
        return matriculaService.atualizarFrequencia(id, request);
    }
}
