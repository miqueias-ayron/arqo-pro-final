package br.com.blackboard.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByCoordenadorId(int idProfessor);
}
