package br.com.blackboard.turma;

import br.com.blackboard.disciplina.DisciplinaResponse;
import br.com.blackboard.sala.Sala;

public class TurmaResponse {
    private int id;
    private String codigo;
    private String semestre;
    private String horario;
    private DisciplinaResponse disciplina;
    private Sala sala;

    public TurmaResponse(Turma turma) {
        this.id = turma.getId();
        this.codigo = turma.getCodigo();
        this.semestre = turma.getSemestre();
        this.horario = turma.getHorario();
        this.disciplina = new DisciplinaResponse(turma.getDisciplina());
        this.sala = turma.getSala();
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getHorario() {
        return horario;
    }

    public DisciplinaResponse getDisciplina() {
        return disciplina;
    }

    public Sala getSala() {
        return sala;
    }
}
