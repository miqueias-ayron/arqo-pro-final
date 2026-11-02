package br.com.blackboard.matricula;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PatchMapping("/{id}/frequencia")
    public MatriculaResponse atualizarFrequencia(@PathVariable int id, @Valid @RequestBody FrequenciaRequest request) {
        return matriculaService.atualizarFrequencia(id, request);
    }
}
