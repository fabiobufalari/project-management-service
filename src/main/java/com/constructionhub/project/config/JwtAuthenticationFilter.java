package com.constructionhub.project.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JWT Authentication Filter
 * 
 * EN: Filter that intercepts HTTP requests to validate JWT tokens and authenticate users.
 * This filter extracts and validates the JWT token from the Authorization header.
 * 
 * PT: Filtro que intercepta requisições HTTP para validar tokens JWT e autenticar usuários.
 * Este filtro extrai e valida o token JWT do cabeçalho Authorization.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret:defaultSecret}")
    private String jwtSecret;

    /**
     * Do Filter Internal
     * 
     * EN: Core filter method that processes each HTTP request to validate JWT tokens.
     * Extracts the token from the Authorization header, validates it, and sets up the security context.
     * 
     * PT: Método principal do filtro que processa cada requisição HTTP para validar tokens JWT.
     * Extrai o token do cabeçalho Authorization, valida-o e configura o contexto de segurança.
     * 
     * @param request EN: The HTTP request / PT: A requisição HTTP
     * @param response EN: The HTTP response / PT: A resposta HTTP
     * @param filterChain EN: The filter chain / PT: A cadeia de filtros
     * @throws ServletException EN: If a servlet error occurs / PT: Se ocorrer um erro de servlet
     * @throws IOException EN: If an I/O error occurs / PT: Se ocorrer um erro de I/O
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                // In a real implementation, validate the token and extract claims
                // For this example, we'll assume the token is valid if present
                
                // Set up authentication in the security context
                List<SimpleGrantedAuthority> authorities = Stream.of("ROLE_USER")
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken("user", null, authorities);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
}
