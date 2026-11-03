package br.com.blackboard.nota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, NotaId> {
    List<Nota> findByMatriculaId(int idMatricula);

    boolean existsByAvaliacaoId(int idAvaliacao);
}
