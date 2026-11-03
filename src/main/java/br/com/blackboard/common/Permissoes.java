package br.com.blackboard.common;

import br.com.blackboard.turma.Turma;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

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

    // Alterações de turma: só o master ou o professor que dá aula nela
    public static void exigirMasterOuProfessorDaTurma(UserDetails usuario, Turma turma) {
        if (!temPerfil(usuario, "MASTER") && !ehProfessorDaTurma(usuario, turma)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Professor só pode alterar dados das turmas em que dá aula");
        }
    }
}
