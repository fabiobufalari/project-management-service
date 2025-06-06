// Path: project-management-service/src/main/java/com/bufalari/building/security/JwtUtil.java
package com.bufalari.building.security; // <<--- Package adjusted

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
// HashMap, Map, Function imports are needed if generating tokens here
// import java.util.HashMap;
// import java.util.Map;
import java.util.function.Function;

/**
 * Utility for handling JWT tokens, using a configured secret key.
 * Utilitário para manipulação de tokens JWT, usando uma chave secreta configurada.
 */
@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    // Inject the secret key from application properties
    // Injeta a chave secreta das propriedades da aplicação
    @Value("${security.jwt.token.secret-key}")// Make sure this property exists in application.yml/properties
    private String configuredSecretKey;

    private SecretKey secretKey; // Use SecretKey type

    /**
     * Initializes the SecretKey after properties injection.
     * Inicializa a SecretKey após a injeção das propriedades.
     */
    @PostConstruct
    public void init() {
        if (configuredSecretKey == null || configuredSecretKey.isBlank()) {
            log.error("JWT secret key is not configured properly in application properties (jwt.secret).");
            throw new IllegalStateException("JWT secret key must be configured.");
        }
        try {
            this.secretKey = Keys.hmacShaKeyFor(configuredSecretKey.getBytes(StandardCharsets.UTF_8));
            log.info("JWT Secret Key initialized successfully for Project Management Service.");
        } catch (Exception e) {
            log.error("Error initializing JWT Secret Key from configured value.", e);
            throw new RuntimeException("Failed to initialize JWT Secret Key", e);
        }
    }

    /**
     * Extracts the username (subject) from the token.
     * Extrai o nome de usuário (subject) do token.
     * @param token The JWT token. / O token JWT.
     * @return The username. / O nome de usuário.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the token.
     * Extrai a data de expiração do token.
     * @param token The JWT token. / O token JWT.
     * @return The expiration date. / A data de expiração.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the token using a claims resolver function.
     * Extrai uma claim específica do token usando uma função resolvedora de claims.
     * @param token The JWT token. / O token JWT.
     * @param claimsResolver Function to extract the claim. / Função para extrair a claim.
     * @param <T> The type of the claim. / O tipo da claim.
     * @return The extracted claim. / A claim extraída.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the token and extracts all claims. Handles potential exceptions.
     * Faz o parse do token e extrai todas as claims. Trata exceções potenciais.
     * @param token The JWT token. / O token JWT.
     * @return The Claims object. / O objeto Claims.
     * @throws JwtException if the token is invalid or cannot be parsed. / Se o token for inválido ou não puder ser parseado.
     */
    private Claims extractAllClaims(String token) throws JwtException {
         try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.secretKey) // Use the initialized key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
         } catch (ExpiredJwtException e) {
             log.warn("JWT token is expired: {}", e.getMessage());
             throw e;
         } catch (UnsupportedJwtException e) {
             log.warn("JWT token is unsupported: {}", e.getMessage());
             throw e;
         } catch (MalformedJwtException e) {
             log.warn("JWT token is malformed: {}", e.getMessage());
             throw e;
         } catch (SignatureException e) {
             log.warn("JWT signature validation failed: {}", e.getMessage());
             throw e;
         } catch (IllegalArgumentException e) {
             log.warn("JWT token argument validation failed: {}", e.getMessage());
             throw e;
         }
    }

    /**
     * Checks if the token is expired.
     * Verifica se o token está expirado.
     * @param token The JWT token. / O token JWT.
     * @return true if the token is expired, false otherwise. / true se o token expirou, false caso contrário.
     */
    private Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            log.warn("Could not determine expiration due to other JWT exception: {}", e.getMessage());
            return true;
        }
    }

    /**
     * Validates the token against UserDetails (username match and expiration).
     * Valida o token em relação ao UserDetails (correspondência de nome de usuário e expiração).
     * @param token The JWT token. / O token JWT.
     * @param userDetails The UserDetails object. / O objeto UserDetails.
     * @return true if the token is valid for the user, false otherwise. / true se o token for válido para o usuário, false caso contrário.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            // Check if userDetails is not null before accessing username
            // Verifica se userDetails não é nulo antes de acessar o username
            if (userDetails == null) {
                 log.warn("UserDetails object provided for validation is null for token subject: {}", username);
                 return false;
            }
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException e) {
            return false;
        }
    }

    // --- Token Generation Methods REMOVED ---
    // --- Métodos de Geração de Token REMOVIDOS ---
    // This service should only validate tokens, not generate them.
    // Este serviço deve apenas validar tokens, não gerá-los.
}