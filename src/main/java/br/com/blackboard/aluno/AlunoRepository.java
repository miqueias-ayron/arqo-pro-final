package br.com.blackboard.aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsByRa(String ra);

    Optional<Aluno> findByUsuarioEmail(String email);

    long countByEventosId(int idEvento);
}
