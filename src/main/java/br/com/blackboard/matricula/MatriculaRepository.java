package br.com.blackboard.matricula;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    boolean existsByAlunoIdAndTurmaId(int idAluno, int idTurma);

    // Carrega aluno, turma, disciplina e professor junto para evitar uma consulta por matrícula
    @EntityGraph(attributePaths = {"aluno", "turma.disciplina.professor.usuario"})
    List<Matricula> findByAlunoIdOrderById(int idAluno);

    @EntityGraph(attributePaths = {"aluno", "turma.disciplina"})
    List<Matricula> findByTurmaIdOrderByAlunoNome(int idTurma);
}
