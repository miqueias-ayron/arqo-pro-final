package br.com.blackboard.organizacaoestudantil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrganizacaoEstudantilService {

    @Autowired
    private OrganizacaoEstudantilRepository organizacaoEstudantilRepository;

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

}
