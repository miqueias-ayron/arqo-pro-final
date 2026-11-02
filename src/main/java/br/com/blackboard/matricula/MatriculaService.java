package br.com.blackboard.matricula;

import br.com.blackboard.nota.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private NotaService notaService;

    @Transactional
    public MatriculaResponse atualizarFrequencia(int id, FrequenciaRequest request) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        matricula.setFrequencia(request.getFrequencia());
        notaService.recalcularStatus(matricula);

        return new MatriculaResponse(matricula);
    }
}
