package br.com.blackboard.professor;

public class ProfessorResponse {
    private int id;
    private String nome;
    private String email;
    private String titulacao;

    public ProfessorResponse(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.email = professor.getUsuario().getEmail();
        this.titulacao = professor.getTitulacao();
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

    public String getTitulacao() {
        return titulacao;
    }
}
