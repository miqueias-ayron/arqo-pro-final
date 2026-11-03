package br.com.blackboard.common;

import br.com.blackboard.usuario.JwtService;
import br.com.blackboard.usuario.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtService.extractUsername(token);
                // Token de um usuário que não existe mais segue como não autenticado e vira 401
                try {
                    UserDetails userDetails = usuarioService.loadUserByUsername(username);
                    // Usuário desativado depois de emitir o token também não autentica
                    if (!userDetails.isEnabled()) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (UsernameNotFoundException e) {
                    SecurityContextHolder.clearContext();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
