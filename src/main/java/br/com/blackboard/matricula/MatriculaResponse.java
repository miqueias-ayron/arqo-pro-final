package br.com.blackboard.matricula;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MatriculaResponse {

    private int id;
    private int idAluno;
    private String nomeAluno;
    private int idTurma;
    private LocalDate data;
    private BigDecimal frequencia;
    private BigDecimal mediaFinal;
    private StatusMatricula status;

    public MatriculaResponse(Matricula matricula) {
        this.id = matricula.getId();
        this.idAluno = matricula.getAluno().getId();
        this.nomeAluno = matricula.getAluno().getNome();
        this.idTurma = matricula.getTurma().getId();
        this.data = matricula.getData();
        this.frequencia = matricula.getFrequencia();
        this.mediaFinal = matricula.getMediaFinal();
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

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getMediaFinal() {
        return mediaFinal;
    }

    public BigDecimal getFrequencia() {
        return frequencia;
    }

    public StatusMatricula getStatus() {
        return status;
    }
}
