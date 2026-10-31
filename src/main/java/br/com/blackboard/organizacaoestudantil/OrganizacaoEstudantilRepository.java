package br.com.blackboard.organizacaoestudantil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoEstudantilRepository extends JpaRepository<OrganizacaoEstudantil, Integer> {
}
