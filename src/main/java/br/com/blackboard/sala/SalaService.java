package br.com.blackboard.sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public Sala create(Sala sala) {
        sala.setId(0);

        if(salaRepository.existsByPredioAndNumero(sala.getPredio(), sala.getNumero())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sala já existente");
        }
        return salaRepository.save(sala);
    }

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(int id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada"));
    }


    @Transactional
    public Sala update(int id, Sala dados) {
        Sala sala = findById(id);

        if (salaRepository.existsByPredioAndNumeroAndIdNot(dados.getPredio(), dados.getNumero(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sala já existente");
        }

        sala.setPredio(dados.getPredio());
        sala.setNumero(dados.getNumero());
        sala.setCapacidade(dados.getCapacidade());
        sala.setTipo(dados.getTipo());
        return salaRepository.save(sala);
    }
}
