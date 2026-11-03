package br.com.blackboard.evento;

import br.com.blackboard.aluno.AlunoRepository;
import br.com.blackboard.aluno.AlunoResumoResponse;

import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantilRepository;
import br.com.blackboard.sala.Sala;
import br.com.blackboard.sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private OrganizacaoEstudantilRepository organizacaoEstudantilRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public EventoResponse create(EventoRequest request) {
        Evento evento = new Evento();
        preencher(evento, request);
        return new EventoResponse(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponse update(int id, EventoRequest request) {
        Evento evento = buscar(id);

        // Não dá para reduzir as vagas abaixo de quem já se inscreveu
        long inscritos = alunoRepository.countByEventosId(id);
        if (request.getVagas() < inscritos) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "O evento já tem " + inscritos + " inscritos; as vagas não podem ficar abaixo disso");
        }

        preencher(evento, request);
        return new EventoResponse(eventoRepository.save(evento));
    }

    @Transactional(readOnly = true)
    public List<AlunoResumoResponse> inscritos(int id) {
        buscar(id);
        return alunoRepository.findByEventosIdOrderByNome(id).stream()
                .map(AlunoResumoResponse::new)
                .toList();
    }

    private Evento buscar(int id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    private void preencher(Evento evento, EventoRequest request) {
        OrganizacaoEstudantil organizacao = organizacaoEstudantilRepository.findById(request.getIdOrganizacao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organização estudantil não encontrada"));
        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sala não encontrada"));

        // Data no passado só é aceita se não mudou (editar um evento que já aconteceu)
        boolean dataMudou = !request.getDataHora().equals(evento.getDataHora());
        if (dataMudou && request.getDataHora().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data do evento não pode estar no passado");
        }

        if (request.getVagas() > sala.getCapacidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A sala comporta " + sala.getCapacidade() + " pessoas; reduza as vagas ou escolha outra sala");
        }

        boolean salaOcupada = evento.getId() == 0
                ? eventoRepository.existsBySalaIdAndDataHora(sala.getId(), request.getDataHora())
                : eventoRepository.existsBySalaIdAndDataHoraAndIdNot(sala.getId(), request.getDataHora(), evento.getId());
        if (salaOcupada) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe outro evento nessa sala nesse horário");
        }

        evento.setNome(request.getNome());
        evento.setDataHora(request.getDataHora());
        evento.setVagas(request.getVagas());
        evento.setOrganizacao(organizacao);
        evento.setSala(sala);
    }

    public List<EventoResponse> findAll() {
        return eventoRepository.findAll().stream()
                .map(EventoResponse::new)
                .toList();
    }

    public EventoResponse findById(int id) {
        return new EventoResponse(buscar(id));
    }

}
