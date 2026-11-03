package br.com.blackboard.matricula;

import java.math.BigDecimal;

public class MatriculaResponse {

    private int id;
    private int idAluno;
    private String nomeAluno;
    private int idTurma;
    private BigDecimal frequencia;
    private StatusMatricula status;

    public MatriculaResponse(Matricula matricula) {
        this.id = matricula.getId();
        this.idAluno = matricula.getAluno().getId();
        this.nomeAluno = matricula.getAluno().getNome();
        this.idTurma = matricula.getTurma().getId();
        this.frequencia = matricula.getFrequencia();
        this.status = matricula.getStatus();
    }

    public int getId() {
        return id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public BigDecimal getFrequencia() {
        return frequencia;
    }

    public StatusMatricula getStatus() {
        return status;
    }
}
