package com.bufalari.building.security;

import com.bufalari.building.DTO.UserDetailsDTO;
import com.bufalari.building.client.AuthServiceClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // Pode ser usado se não usar @RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Service to load user-specific data by making a call to the Authentication Service.
 * This implementation is used by Spring Security during JWT validation.
 * Serviço para carregar dados específicos do usuário fazendo uma chamada ao Serviço de Autenticação.
 * Esta implementação é usada pelo Spring Security durante a validação do JWT.
 */
@Service("projectUserDetailsService") // <<<--- NOME DO BEAN DEFINIDO AQUI
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AuthServiceClient authServiceClient;

    @Autowired // Ou use @RequiredArgsConstructor na classe se preferir
    public CustomUserDetailsService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user details for username: {}", username);
        UserDetailsDTO userDetailsDTO = null;
        try {
            userDetailsDTO = authServiceClient.getUserByUsername(username);

            if (userDetailsDTO == null) {
                log.warn("User details not found for username: {} (Auth service returned null)", username);
                throw new UsernameNotFoundException("User not found: " + username);
            }

            log.info("Successfully loaded user details for username: {}", username);
            return new User(
                    userDetailsDTO.getUsername(),
                    userDetailsDTO.getPassword() != null ? userDetailsDTO.getPassword() : "",
                    userDetailsDTO.getRoles() != null ?
                        userDetailsDTO.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                            .collect(Collectors.toList())
                        : Collections.emptyList()
            );

        } catch (FeignException.NotFound e) {
            log.warn("User not found via auth service for username: {}. Feign status: 404", username, e);
            throw new UsernameNotFoundException("User not found: " + username, e);
        } catch (FeignException e) {
            log.error("Error calling authentication service for username: {}. Status: {}, Message: {}", username, e.status(), e.getMessage(), e);
            throw new UsernameNotFoundException("Failed to load user details due to communication error with auth service for user: " + username, e);
        } catch (Exception e) {
            log.error("Unexpected error loading user details for username: {}", username, e);
            throw new UsernameNotFoundException("Failed to load user details for user: " + username, e);
        }
    }
}