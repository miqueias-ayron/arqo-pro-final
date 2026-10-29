package br.com.blackboard.professor;

import br.com.blackboard.usuario.Usuario;
import br.com.blackboard.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cria o usuário e o professor juntos: se um falhar, nenhum é salvo
    @Transactional
    public ProfessorResponse create(ProfessorRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario = usuarioRepository.save(usuario);

        Professor professor = new Professor();
        professor.setUsuario(usuario);
        professor.setNome(request.getNome());
        professor.setTitulacao(request.getTitulacao());

        return new ProfessorResponse(professorRepository.save(professor));
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> findAll() {
        return professorRepository.findAll().stream()
                .map(ProfessorResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponse findById(int id) {
        return professorRepository.findById(id)
                .map(ProfessorResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }
}
