package br.com.blackboard.avaliacao;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AvaliacaoRequest {

    @NotBlank
    @Size(max = 255)
    private String tipo;

    @NotNull
    private LocalDate data;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax("1.00")
    @Digits(integer = 1, fraction = 2)
    private BigDecimal peso;

    @NotNull
    private Integer idTurma;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }
}
