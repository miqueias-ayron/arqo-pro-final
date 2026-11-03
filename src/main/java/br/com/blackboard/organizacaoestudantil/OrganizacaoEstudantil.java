package br.com.blackboard.organizacaoestudantil;

import org.hibernate.annotations.Formula;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity(name = "tb_organizacao_estudantil")
public class OrganizacaoEstudantil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false)
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    @Size(max = 255)
    private String tipo;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataFundacao;

    // Calculado pelo banco a cada leitura; não vira coluna
    @Formula("(select count(*) from tb_membro_organizacao m where m.id_organizacao = id)")
    private int totalMembros;

    public int getTotalMembros() {
        return totalMembros;
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }
}
