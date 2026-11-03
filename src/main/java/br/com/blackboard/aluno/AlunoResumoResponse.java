package br.com.blackboard.aluno;

// Dados mínimos do aluno para listas de membros e de inscritos
public class AlunoResumoResponse {

    private int id;
    private String nome;
    private String ra;
    private String email;

    public AlunoResumoResponse(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.ra = aluno.getRa();
        this.email = aluno.getUsuario().getEmail();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRa() {
        return ra;
    }

    public String getEmail() {
        return email;
    }
}
