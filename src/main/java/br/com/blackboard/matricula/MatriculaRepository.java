package br.com.blackboard.matricula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    boolean existsByAlunoIdAndTurmaId(int idAluno, int idTurma);

    List<Matricula> findByAlunoIdOrderById(int idAluno);

    List<Matricula> findByTurmaIdOrderByAlunoNome(int idTurma);
}
