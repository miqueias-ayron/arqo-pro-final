package br.com.blackboard.usuario;

public class UsuarioResponse {
    private int id;
    private String email;
    private Perfil perfil;
    private boolean ativo;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
        this.ativo = usuario.isAtivo();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
