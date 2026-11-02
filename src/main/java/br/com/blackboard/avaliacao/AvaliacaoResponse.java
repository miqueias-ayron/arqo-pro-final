package br.com.blackboard.avaliacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AvaliacaoResponse {

    private int id;
    private String tipo;
    private LocalDate data;
    private BigDecimal peso;

    public AvaliacaoResponse(Avaliacao avaliacao) {
        this.id = avaliacao.getId();
        this.tipo = avaliacao.getTipo();
        this.data = avaliacao.getData();
        this.peso = avaliacao.getPeso();
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getPeso() {
        return peso;
    }
}