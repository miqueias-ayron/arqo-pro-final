package br.com.blackboard.matricula;

import br.com.blackboard.aluno.Aluno;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

public final class PermissaoMatricula {

    private PermissaoMatricula() {
    }

    // Master e professor consultam qualquer aluno; o aluno consulta apenas os próprios dados
    public static void validarConsulta(Aluno aluno, UserDetails usuario) {
        boolean masterOuProfessor = usuario.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_MASTER".equals(authority.getAuthority())
                        || "ROLE_PROFESSOR".equals(authority.getAuthority()));

        if (!masterOuProfessor && !aluno.getUsuario().getEmail().equals(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para acessar este aluno");
        }
    }
}
