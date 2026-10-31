package br.com.blackboard.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CursoRequest {

    @NotBlank(message = "Nome não pode ser nulo nem vazio")
    private String nome;

    @Positive(message = "Quantidade de semestres deve ser positiva")
    private int duracaoSemestres;

    @NotNull(message = "id do coordenador não pode ser nulo")
    private Integer idCoordenador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracaoSemestres() {
        return duracaoSemestres;
    }

    public void setDuracaoSemestres(int duracaoSemestres) {
        this.duracaoSemestres = duracaoSemestres;
    }

    public Integer getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(Integer idCoordenador) {
        this.idCoordenador = idCoordenador;
    }
}
