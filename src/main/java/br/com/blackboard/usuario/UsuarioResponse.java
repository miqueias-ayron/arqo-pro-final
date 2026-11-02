package br.com.blackboard.usuario;

public class UsuarioResponse {
    private int id;
    private String email;
    private Perfil perfil;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
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
}
