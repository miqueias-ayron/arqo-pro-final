package br.com.blackboard.nota;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping("/notas")
    public NotaResponse lancar(@Valid @RequestBody NotaRequest request) {
        return notaService.lancar(request);
    }

    @GetMapping("/matriculas/{idMatricula}/notas")
    public BoletimResponse boletim(@PathVariable int idMatricula) {
        return notaService.boletim(idMatricula);
    }
}
