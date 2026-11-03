package br.com.blackboard.avaliacao;

import br.com.blackboard.nota.NotaRepository;
import br.com.blackboard.turma.Turma;
import br.com.blackboard.turma.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Transactional
    public AvaliacaoResponse create(AvaliacaoRequest request) {
        Turma turma = buscarTurma(request.getIdTurma());
        validarSomaDosPesos(turma, request.getPeso(), 0);

        Avaliacao avaliacao = new Avaliacao();
        preencher(avaliacao, request, turma);
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
        Turma turma = buscarTurma(request.getIdTurma());

        // Mudar peso ou turma depois de lançar notas alteraria médias e status já calculados
        boolean mudouPesoOuTurma = avaliacao.getPeso().compareTo(request.getPeso()) != 0
                || avaliacao.getTurma().getId() != turma.getId();
        if (mudouPesoOuTurma && notaRepository.existsByAvaliacaoId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível alterar peso ou turma de avaliação com notas lançadas");
        }

        validarSomaDosPesos(turma, request.getPeso(), id);
        preencher(avaliacao, request, turma);
        return new AvaliacaoResponse(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public void delete(int id) {
        avaliacaoRepository.delete(buscar(id));
    }

    // As avaliações são criadas uma a uma, então a soma pode ficar abaixo de 1, mas nunca acima
    private void validarSomaDosPesos(Turma turma, BigDecimal peso, int idIgnorado) {
        BigDecimal soma = avaliacaoRepository.findByTurmaId(turma.getId()).stream()
                .filter(avaliacao -> avaliacao.getId() != idIgnorado)
                .map(Avaliacao::getPeso)
                .reduce(peso, BigDecimal::add);

        if (soma.compareTo(BigDecimal.ONE) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A soma dos pesos das avaliações da turma não pode passar de 1");
        }
    }

    private Avaliacao buscar(int id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));
    }

    private Turma buscarTurma(int id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
    }

    private void preencher(Avaliacao avaliacao, AvaliacaoRequest request, Turma turma) {
        avaliacao.setTipo(request.getTipo());
        avaliacao.setData(request.getData());
        avaliacao.setPeso(request.getPeso());
        avaliacao.setTurma(turma);
    }
}
