package br.com.blackboard.organizacaoestudantil;

import br.com.blackboard.aluno.AlunoRepository;
import br.com.blackboard.aluno.AlunoResumoResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrganizacaoEstudantilService {

    @Autowired
    private OrganizacaoEstudantilRepository organizacaoEstudantilRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public OrganizacaoEstudantil create(OrganizacaoEstudantil organizacao) {
        organizacao.setId(0);

        return organizacaoEstudantilRepository.save(organizacao);
    }

    public List<OrganizacaoEstudantil> findAll() {
        return organizacaoEstudantilRepository.findAll();
    }

    public OrganizacaoEstudantil findById(int id) {
        return organizacaoEstudantilRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organização estudantil não encontrada"));
    }


    @Transactional
    public OrganizacaoEstudantil update(int id, OrganizacaoEstudantil dados) {
        OrganizacaoEstudantil organizacao = findById(id);
        organizacao.setNome(dados.getNome());
        organizacao.setTipo(dados.getTipo());
        organizacao.setDataFundacao(dados.getDataFundacao());
        return organizacaoEstudantilRepository.save(organizacao);
    }

    @Transactional(readOnly = true)
    public List<AlunoResumoResponse> membros(int id) {
        findById(id);
        return alunoRepository.findByOrganizacoesIdOrderByNome(id).stream()
                .map(AlunoResumoResponse::new)
                .toList();
    }
}
