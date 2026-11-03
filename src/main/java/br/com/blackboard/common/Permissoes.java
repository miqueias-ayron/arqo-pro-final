package br.com.blackboard.common;

import br.com.blackboard.turma.Turma;
import org.springframework.security.core.userdetails.UserDetails;

public final class Permissoes {

    private Permissoes() {
    }

    public static boolean temPerfil(UserDetails usuario, String perfil) {
        String role = "ROLE_" + perfil;
        return usuario.getAuthorities().stream()
                .anyMatch(authority -> role.equals(authority.getAuthority()));
    }

    // Professor que dá aula na turma (professor da disciplina da turma)
    public static boolean ehProfessorDaTurma(UserDetails usuario, Turma turma) {
        return turma.getDisciplina().getProfessor().getUsuario().getEmail().equals(usuario.getUsername());
    }
}
