package br.com.blackboard.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmailAndIdNot(String email, int id);

    Optional<Usuario> findByEmail(String username);
}
