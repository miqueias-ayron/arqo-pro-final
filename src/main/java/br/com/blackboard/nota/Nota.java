package br.com.blackboard.nota;

import br.com.blackboard.avaliacao.Avaliacao;
import br.com.blackboard.matricula.Matricula;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity(name = "tb_nota")
public class Nota {

    @EmbeddedId
    private NotaId id = new NotaId();

    @MapsId("idMatricula")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;

    @MapsId("idAvaliacao")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_avaliacao")
    private Avaliacao avaliacao;

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal valor;

    public NotaId getId() {
        return id;
    }

    public void setId(NotaId id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}