package br.com.blackboard.disciplina;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DisciplinaRequest {

    @NotBlank(message = "Código não pode ser nulo nem vazio")
    private String codigo;

    @NotBlank(message = "Nome não pode ser nulo nem vazio")
    private String nome;

    @Positive(message = "Carga horária deve ser positiva")
    private int cargaHoraria;

    @NotNull(message = "id do professor não pode ser nulo")
    private Integer idProfessor;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }
}
