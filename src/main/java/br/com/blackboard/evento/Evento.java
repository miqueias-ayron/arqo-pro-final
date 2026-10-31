package br.com.blackboard.evento;

import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.sala.Sala;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity(name = "tb_evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Positive
    @Column(nullable = false)
    private int vagas;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_organizacao", nullable = false)
    private OrganizacaoEstudantil organizacao;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public OrganizacaoEstudantil getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(OrganizacaoEstudantil organizacao) {
        this.organizacao = organizacao;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
