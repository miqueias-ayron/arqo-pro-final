package br.com.blackboard.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios", "/professores", "/alunos", "/cursos", "/salas",
                                "/organizacoes-estudantis", "/eventos", "/disciplinas", "/turmas").hasRole("MASTER")
                        .requestMatchers(HttpMethod.GET, "/alunos").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/avaliacoes").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers(HttpMethod.PUT, "/avaliacoes/**").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers(HttpMethod.DELETE, "/avaliacoes/**").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers(HttpMethod.POST, "/matriculas").hasRole("MASTER")
                        .requestMatchers(HttpMethod.POST, "/notas").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers(HttpMethod.PATCH, "/matriculas/*/frequencia").hasAnyRole("MASTER", "PROFESSOR")
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/swagger-ui.html",
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}