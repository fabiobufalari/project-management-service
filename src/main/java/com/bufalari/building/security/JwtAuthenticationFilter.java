package com.bufalari.building.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Spring Security filter that intercepts requests to validate the JWT token.
 * If valid, it sets the authentication context.
 * Filtro do Spring Security que intercepta requisições para validar o token JWT.
 * Se válido, define o contexto de autenticação.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    // Inject UserDetailsService (which uses AuthServiceClient)
    // Injeta UserDetailsService (que usa AuthServiceClient)
    private final UserDetailsService userDetailsService;

    // Constructor Injection / Injeção via Construtor
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming requests, extracts and validates JWT, and sets SecurityContext.
     * Filtra requisições recebidas, extrai e valida JWT, e define o SecurityContext.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTH_HEADER);
        final String jwt;
        String username = null; // Initialize username as null

        // If no Authorization header or not starting with Bearer, pass through
        // Se não houver cabeçalho Authorization ou não começar com Bearer, passa adiante
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.trace("No JWT token found in Authorization header, proceeding with filter chain.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(BEARER_PREFIX.length()); // Extract token

        try {
            username = jwtUtil.extractUsername(jwt); // Extract username from token
            log.trace("Extracted username '{}' from JWT.", username);

            // If username exists and there's no authentication in the current security context
            // Se o username existe e não há autenticação no contexto de segurança atual
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details via UserDetailsService (which calls AuthServiceClient)
                // Carrega detalhes do usuário via UserDetailsService (que chama AuthServiceClient)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validate token signature, expiration, and username match
                // Valida assinatura do token, expiração e correspondência de username
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    // Create authentication token
                    // Cria token de autenticação
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, // Principal (can be username string or UserDetails object)
                            null,        // Credentials (not needed for JWT)
                            userDetails.getAuthorities() // Authorities from UserDetails
                    );
                    // Set details (like IP address, session ID) from the request
                    // Define detalhes (como endereço IP, ID da sessão) da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    // Define a autenticação no SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("JWT token validated successfully for user: {}", username);
                } else {
                     log.warn("JWT token validation failed for extracted user: {}", username);
                }
            } else if (username == null) {
                log.warn("Could not extract username from JWT.");
            } // If SecurityContext already has authentication, do nothing (already authenticated)

        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            // Optionally set an error response attribute for an AuthenticationEntryPoint/AccessDeniedHandler
            // request.setAttribute("jwt_error", "Token expired");
        } catch (UsernameNotFoundException e) {
             log.warn("User '{}' not found via UserDetailsService: {}", username, e.getMessage());
             // request.setAttribute("jwt_error", "User not found");
        } catch (JwtException e) {
            log.error("Error processing JWT token: {}", e.getMessage());
            // request.setAttribute("jwt_error", "Invalid token");
        } catch (Exception e) {
            // Catch unexpected errors during user loading or validation
             log.error("Unexpected error during JWT filter processing for user '{}'", username, e);
             // request.setAttribute("jwt_error", "Internal error during authentication");
        }

        // Continue the filter chain regardless of authentication outcome
        // Continua a cadeia de filtros independentemente do resultado da autenticação
        // Access control decisions happen later based on SecurityContext
        // Decisões de controle de acesso acontecem depois com base no SecurityContext
        filterChain.doFilter(request, response);
    }
}