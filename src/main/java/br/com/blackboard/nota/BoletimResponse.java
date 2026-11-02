package br.com.blackboard.nota;

import br.com.blackboard.matricula.Matricula;
import br.com.blackboard.matricula.StatusMatricula;

import java.math.BigDecimal;
import java.util.List;

public class BoletimResponse {

    private int idMatricula;
    private List<NotaResponse> notas;
    private BigDecimal media;
    private BigDecimal frequencia;
    private StatusMatricula status;

    public BoletimResponse(Matricula matricula, List<Nota> notas) {
        this.idMatricula = matricula.getId();
        this.notas = notas.stream().map(NotaResponse::new).toList();
        this.media = CalculadoraMedia.calcularMedia(notas);
        this.frequencia = matricula.getFrequencia();
        this.status = matricula.getStatus();
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public List<NotaResponse> getNotas() {
        return notas;
    }

    public BigDecimal getMedia() {
        return media;
    }

    public BigDecimal getFrequencia() {
        return frequencia;
    }

    public StatusMatricula getStatus() {
        return status;
    }
}
