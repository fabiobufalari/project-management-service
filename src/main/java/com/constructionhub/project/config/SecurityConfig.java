package com.constructionhub.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration
 * 
 * EN: Configuration class for Spring Security.
 * Defines security rules, authentication mechanisms, and access control for the Project Service.
 * 
 * PT: Classe de configuração para o Spring Security.
 * Define regras de segurança, mecanismos de autenticação e controle de acesso para o Serviço de Projetos.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * EN: Constructor with dependency injection
     * PT: Construtor com injeção de dependência
     * 
     * @param jwtAuthFilter EN: JWT authentication filter / PT: Filtro de autenticação JWT
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Security Filter Chain
     * 
     * EN: Configures the security filter chain with authorization rules and JWT authentication.
     * 
     * PT: Configura a cadeia de filtros de segurança com regras de autorização e autenticação JWT.
     * 
     * @param http EN: HttpSecurity object to configure / PT: Objeto HttpSecurity para configurar
     * @return EN: The configured security filter chain / PT: A cadeia de filtros de segurança configurada
     * @throws Exception EN: If configuration fails / PT: Se a configuração falhar
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/projects/**").authenticated()
                .requestMatchers("/api/tasks/**").authenticated()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
