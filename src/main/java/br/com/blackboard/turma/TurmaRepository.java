package br.com.blackboard.turma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    // mesma disciplina não pode repetir o código no mesmo semestre
    boolean existsByCodigoAndDisciplinaIdAndSemestre(String codigo, int idDisciplina, String semestre);

    // a mesma sala não pode ter duas turmas no mesmo semestre e horário
    boolean existsBySalaIdAndSemestreAndHorario(int idSala, String semestre, String horario);

}
