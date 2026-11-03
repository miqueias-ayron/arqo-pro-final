package br.com.blackboard.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TurmaRequest {

    @NotBlank(message = "Código não pode ser nulo nem vazio")
    private String codigo;

    @NotBlank(message = "Semestre não pode ser nulo nem vazio")
    private String semestre;

    @NotBlank(message = "Horário não pode ser nulo nem vazio")
    private String horario;

    @NotNull(message = "id da disciplina não pode ser nulo")
    private Integer idDisciplina;

    @NotNull(message = "id da sala não pode ser nulo")
    private Integer idSala;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}
