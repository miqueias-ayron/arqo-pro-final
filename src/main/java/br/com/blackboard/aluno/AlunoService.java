package br.com.blackboard.aluno;

import br.com.blackboard.curso.Curso;
import br.com.blackboard.curso.CursoRepository;
import br.com.blackboard.evento.Evento;
import br.com.blackboard.evento.EventoRepository;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantil;
import br.com.blackboard.organizacaoestudantil.OrganizacaoEstudantilRepository;
import br.com.blackboard.usuario.Perfil;
import br.com.blackboard.usuario.Usuario;
import br.com.blackboard.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private OrganizacaoEstudantilRepository organizacaoEstudantilRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cria o usuário e o aluno juntos: se um falhar, nenhum é salvo
    @Transactional
    public AlunoResponse create(AlunoRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        if (alunoRepository.existsByRa(request.getRa())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "RA já cadastrado");
        }

        Curso curso = cursoRepository.findById(request.getIdCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setPerfil(Perfil.ALUNO);
        usuario = usuarioRepository.save(usuario);

        Aluno aluno = new Aluno();
        aluno.setUsuario(usuario);
        aluno.setNome(request.getNome());
        aluno.setRa(request.getRa());
        aluno.setDataIngresso(request.getDataIngresso());
        aluno.setCurso(curso);

        return new AlunoResponse(alunoRepository.save(aluno));
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> findAll() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponse findById(int id, UserDetails usuario) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        if (!temPerfil(usuario, "ROLE_PROFESSOR")) {
            validarPermissao(aluno, usuario);
        }

        return new AlunoResponse(aluno);
    }

    @Transactional
    public AlunoResponse addOrganizacao(int id, int idOrganizacao, UserDetails usuario) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        validarPermissao(aluno, usuario);

        OrganizacaoEstudantil organizacao = organizacaoEstudantilRepository.findById(idOrganizacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organização estudantil não encontrada"));

        if (!aluno.getOrganizacoes().add(organizacao)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já é membro da organização");
        }

        return new AlunoResponse(alunoRepository.save(aluno));
    }

    @Transactional
    public AlunoResponse addEvento(int id, int idEvento, UserDetails usuario) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        validarPermissao(aluno, usuario);

        // Trava o evento até o fim da transação para inscrições simultâneas não ultrapassarem as vagas
        Evento evento = eventoRepository.findWithLockById(idEvento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));

        if (aluno.getEventos().contains(evento)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já inscrito no evento");
        }

        if (alunoRepository.countByEventosId(idEvento) >= evento.getVagas()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Evento sem vagas disponíveis");
        }

        aluno.getEventos().add(evento);

        return new AlunoResponse(alunoRepository.save(aluno));
    }

    // Apenas o master ou o próprio aluno podem acessar os dados e alterar os vínculos do aluno
    private void validarPermissao(Aluno aluno, UserDetails usuario) {
        if (!temPerfil(usuario, "ROLE_MASTER") && !aluno.getUsuario().getEmail().equals(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão para acessar este aluno");
        }
    }

    private boolean temPerfil(UserDetails usuario, String perfil) {
        return usuario.getAuthorities().stream()
                .anyMatch(authority -> perfil.equals(authority.getAuthority()));
    }

    @Transactional
    public AlunoResponse update(int id, AlunoUpdateRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        Usuario usuario = aluno.getUsuario();
        if (usuarioRepository.existsByEmailAndIdNot(request.getEmail(), usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        if (alunoRepository.existsByRaAndIdNot(request.getRa(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "RA já cadastrado");
        }

        Curso curso = cursoRepository.findById(request.getIdCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        usuario.setEmail(request.getEmail());
        aluno.setNome(request.getNome());
        aluno.setRa(request.getRa());
        aluno.setDataIngresso(request.getDataIngresso());
        aluno.setCurso(curso);
        return new AlunoResponse(alunoRepository.save(aluno));
    }
}
