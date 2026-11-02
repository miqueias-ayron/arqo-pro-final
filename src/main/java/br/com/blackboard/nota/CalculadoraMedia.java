package br.com.blackboard.nota;

import br.com.blackboard.matricula.StatusMatricula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class CalculadoraMedia {

    public static final BigDecimal MEDIA_MINIMA = new BigDecimal("6.00");
    public static final BigDecimal FREQUENCIA_MINIMA = new BigDecimal("75.00");

    private CalculadoraMedia() {
    }

    public static BigDecimal calcularMedia(List<Nota> notas) {
        BigDecimal media = BigDecimal.ZERO;

        for (Nota nota : notas) {
            media =
                    media.add(nota.getValor().multiply(nota.getAvaliacao().getPeso()));
        }

        return media.setScale(2, RoundingMode.HALF_UP);
    }

    public static StatusMatricula definirStatus(BigDecimal media, BigDecimal
            frequencia) {
        boolean mediaSuficiente = media.compareTo(MEDIA_MINIMA) >= 0;
        boolean frequenciaSuficiente = frequencia.compareTo(FREQUENCIA_MINIMA) >=
                0;

        return mediaSuficiente && frequenciaSuficiente
                ? StatusMatricula.APROVADO
                : StatusMatricula.REPROVADO;
    }

}