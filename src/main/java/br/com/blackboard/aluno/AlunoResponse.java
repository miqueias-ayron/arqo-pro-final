package br.com.blackboard.aluno;

import br.com.blackboard.curso.CursoResponse;
import br.com.blackboard.evento.EventoResponse;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;

import java.time.LocalDate;
import java.util.List;

public class AlunoResponse {
    private int id;
    private String nome;
    private String email;
    private String ra;
    private LocalDate dataIngresso;
    private CursoResponse curso;
    private List<OrganizacaoEstudantil> organizacoes;
    private List<EventoResponse> eventos;

    public AlunoResponse(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.email = aluno.getUsuario().getEmail();
        this.ra = aluno.getRa();
        this.dataIngresso = aluno.getDataIngresso();
        this.curso = new CursoResponse(aluno.getCurso());
        this.organizacoes = aluno.getOrganizacoes().stream().toList();
        this.eventos = aluno.getEventos().stream()
                .map(EventoResponse::new)
                .toList();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRa() {
        return ra;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public CursoResponse getCurso() {
        return curso;
    }

    public List<OrganizacaoEstudantil> getOrganizacoes() {
        return organizacoes;
    }

    public List<EventoResponse> getEventos() {
        return eventos;
    }
}
