package br.com.blackboard.avaliacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public AvaliacaoResponse create(AvaliacaoRequest request) {
        Avaliacao avaliacao = new Avaliacao();
        preencher(avaliacao, request);
        return new AvaliacaoResponse(avaliacaoRepository.save(avaliacao));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponse> findAll() {
        return avaliacaoRepository.findAll().stream()
                .map(AvaliacaoResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponse findById(int id) {
        return new AvaliacaoResponse(buscar(id));
    }

    @Transactional
    public AvaliacaoResponse update(int id, AvaliacaoRequest request) {
        Avaliacao avaliacao = buscar(id);
        preencher(avaliacao, request);
        return new AvaliacaoResponse(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public void delete(int id) {
        avaliacaoRepository.delete(buscar(id));
    }

    private Avaliacao buscar(int id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Avaliação não encontrada"));
    }

    private void preencher(Avaliacao avaliacao, AvaliacaoRequest request) {
        avaliacao.setTipo(request.getTipo());
        avaliacao.setData(request.getData());
        avaliacao.setPeso(request.getPeso());
    }
}