package br.com.blackboard.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Cria o usuário master na inicialização, a partir de MASTER_EMAIL e MASTER_SENHA
@Component
public class MasterUserInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MasterUserInitializer.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${master.email:}")
    private String email;

    @Value("${master.senha:}")
    private String senha;

    @Override
    public void run(ApplicationArguments args) {
        if (email.isBlank() || senha.isBlank()) {
            log.warn("MASTER_EMAIL/MASTER_SENHA não definidos: usuário master não foi criado");
            return;
        }

        if (usuarioRepository.findByEmail(email).isPresent()) {
            return;
        }

        Usuario master = new Usuario();
        master.setEmail(email);
        master.setSenha(passwordEncoder.encode(senha));
        master.setPerfil(Perfil.MASTER);
        usuarioRepository.save(master);
        log.info("Usuário master criado: {}", email);
    }
}
