package br.com.blackboard.evento;

import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantilRepository;
import br.com.blackboard.sala.Sala;
import br.com.blackboard.sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private OrganizacaoEstudantilRepository organizacaoEstudantilRepository;

    @Autowired
    private SalaRepository salaRepository;

    public EventoResponse create(EventoRequest request) {
        OrganizacaoEstudantil organizacao = organizacaoEstudantilRepository.findById(request.getIdOrganizacao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organização estudantil não encontrada"));

        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada"));

        Evento evento = new Evento();
        evento.setNome(request.getNome());
        evento.setDataHora(request.getDataHora());
        evento.setVagas(request.getVagas());
        evento.setOrganizacao(organizacao);
        evento.setSala(sala);

        return new EventoResponse(eventoRepository.save(evento));
    }

    public List<EventoResponse> findAll() {
        return eventoRepository.findAll().stream()
                .map(EventoResponse::new)
                .toList();
    }

    public EventoResponse findById(int id) {
        return eventoRepository.findById(id)
                .map(EventoResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

}
