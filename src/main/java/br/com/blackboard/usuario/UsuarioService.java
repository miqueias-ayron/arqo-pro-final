package br.com.blackboard.usuario;

import br.com.blackboard.aluno.AlunoRepository;
import br.com.blackboard.professor.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public UsuarioResponse create(UsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setPerfil(request.getPerfil());

        return new UsuarioResponse(usuarioRepository.save(usuario));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario userDTO = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(userDTO.getEmail())
                .password(userDTO.getSenha())
                .roles(userDTO.getPerfil().name())
                .disabled(!userDTO.isAtivo())
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            UserDetails userDetails = this.loadUserByUsername(loginRequest.getEmail());

            if (!userDetails.isEnabled() || !passwordEncoder.matches(loginRequest.getSenha(), userDetails.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
            }

            String token = jwtService.generateToken(loginRequest.getEmail());
            return new LoginResponse(token);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }
    }


    public MeResponse me(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

        return switch (usuario.getPerfil()) {
            case ALUNO -> alunoRepository.findByUsuarioEmail(email)
                    .map(aluno -> new MeResponse(usuario, aluno.getId(), null, aluno.getNome()))
                    .orElseGet(() -> new MeResponse(usuario, null, null, null));
            case PROFESSOR -> professorRepository.findByUsuarioEmail(email)
                    .map(professor -> new MeResponse(usuario, null, professor.getId(), professor.getNome()))
                    .orElseGet(() -> new MeResponse(usuario, null, null, null));
            case MASTER -> new MeResponse(usuario, null, null, null);
        };
    }
}
