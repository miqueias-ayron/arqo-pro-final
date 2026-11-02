package br.com.blackboard.nota;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NotaId implements Serializable {

    @Column(name = "id_matricula")
    private int idMatricula;

    @Column(name = "id_avaliacao")
    private int idAvaliacao;

    public NotaId() {
    }

    public NotaId(int idMatricula, int idAvaliacao) {
        this.idMatricula = idMatricula;
        this.idAvaliacao = idAvaliacao;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaId other)) return false;
        return idMatricula == other.idMatricula && idAvaliacao ==
                other.idAvaliacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatricula, idAvaliacao);
    }
}