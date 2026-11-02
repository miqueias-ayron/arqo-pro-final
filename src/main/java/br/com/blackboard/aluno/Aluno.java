package br.com.blackboard.aluno;

import br.com.blackboard.curso.Curso;
import br.com.blackboard.evento.Evento;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String ra;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataIngresso;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @ManyToMany
    @JoinTable(name = "tb_membro_organizacao",
            joinColumns = @JoinColumn(name = "id_aluno"),
            inverseJoinColumns = @JoinColumn(name = "id_organizacao"))
    private Set<OrganizacaoEstudantil> organizacoes = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_inscricao_evento",
            joinColumns = @JoinColumn(name = "id_aluno"),
            inverseJoinColumns = @JoinColumn(name = "id_evento"))
    private Set<Evento> eventos = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<OrganizacaoEstudantil> getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(Set<OrganizacaoEstudantil> organizacoes) {
        this.organizacoes = organizacoes;
    }

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }
}
