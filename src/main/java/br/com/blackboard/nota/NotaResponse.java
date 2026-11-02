package br.com.blackboard.nota;

import java.math.BigDecimal;

public class NotaResponse {

    private int idMatricula;
    private int idAvaliacao;
    private String tipoAvaliacao;
    private BigDecimal peso;
    private BigDecimal valor;

    public NotaResponse(Nota nota) {
        this.idMatricula = nota.getMatricula().getId();
        this.idAvaliacao = nota.getAvaliacao().getId();
        this.tipoAvaliacao = nota.getAvaliacao().getTipo();
        this.peso = nota.getAvaliacao().getPeso();
        this.valor = nota.getValor();
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
