package br.com.blackboard.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EventoRequest {

    @NotBlank(message = "Nome não pode ser nulo nem vazio")
    private String nome;

    @NotNull(message = "Data e hora não podem ser nulas")
    private LocalDateTime dataHora;

    @Positive(message = "Quantidade de vagas deve ser positiva")
    private int vagas;

    @NotNull(message = "id da organização não pode ser nulo")
    private Integer idOrganizacao;

    @NotNull(message = "id da sala não pode ser nulo")
    private Integer idSala;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public Integer getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(Integer idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}
