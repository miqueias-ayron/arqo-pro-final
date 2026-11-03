package br.com.blackboard.disciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, int id);
}
