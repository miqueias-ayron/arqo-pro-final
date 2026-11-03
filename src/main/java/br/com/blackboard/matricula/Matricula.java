package br.com.blackboard.matricula;

import br.com.blackboard.aluno.Aluno;
import br.com.blackboard.turma.Turma;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "tb_matricula")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_turma"}))
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    private LocalDate data = LocalDate.now();

    // Preenchida quando todas as avaliações da turma têm nota
    @Column(precision = 4, scale = 2)
    private BigDecimal mediaFinal;

    @NotNull
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal frequencia = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMatricula status = StatusMatricula.CURSANDO;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(BigDecimal mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public BigDecimal getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(BigDecimal frequencia) {
        this.frequencia = frequencia;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }
}