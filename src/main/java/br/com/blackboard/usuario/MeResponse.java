package br.com.blackboard.usuario;

// Dados do usuário logado; idAluno e idProfessor vêm nulos quando não se aplicam ao perfil
public class MeResponse {

    private int id;
    private String email;
    private Perfil perfil;
    private boolean ativo;
    private Integer idAluno;
    private Integer idProfessor;
    private String nome;

    public MeResponse(Usuario usuario, Integer idAluno, Integer idProfessor, String nome) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
        this.ativo = usuario.isAtivo();
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public String getNome() {
        return nome;
    }
}
