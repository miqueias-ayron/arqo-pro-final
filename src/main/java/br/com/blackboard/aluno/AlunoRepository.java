package br.com.blackboard.aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByRa(String ra);

    long countByEventosId(int idEvento);
}
