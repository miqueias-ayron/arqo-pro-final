package br.com.blackboard.matricula;

import jakarta.validation.constraints.NotNull;

public class MatriculaRequest {

    @NotNull
    private Integer idAluno;

    @NotNull
    private Integer idTurma;

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }
}
