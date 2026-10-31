package br.com.blackboard.evento;

import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.sala.Sala;

import java.time.LocalDateTime;

public class EventoResponse {
    private int id;
    private String nome;
    private LocalDateTime dataHora;
    private int vagas;
    private OrganizacaoEstudantil organizacao;
    private Sala sala;

    public EventoResponse(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.dataHora = evento.getDataHora();
        this.vagas = evento.getVagas();
        this.organizacao = evento.getOrganizacao();
        this.sala = evento.getSala();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getVagas() {
        return vagas;
    }

    public OrganizacaoEstudantil getOrganizacao() {
        return organizacao;
    }

    public Sala getSala() {
        return sala;
    }
}
