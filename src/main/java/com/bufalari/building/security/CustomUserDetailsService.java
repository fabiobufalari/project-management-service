package com.bufalari.building.security;

import com.bufalari.building.DTO.UserDetailsDTO;
import com.bufalari.building.client.AuthServiceClient; // Correct import
import feign.FeignException; // Import FeignException
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Import for emptyList
import java.util.stream.Collectors;

/**
 * Service to load user-specific data by making a call to the Authentication Service.
 * This implementation is used by Spring Security during JWT validation.
 * Serviço para carregar dados específicos do usuário fazendo uma chamada ao Serviço de Autenticação.
 * Esta implementação é usada pelo Spring Security durante a validação do JWT.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AuthServiceClient authServiceClient;

    // Constructor Injection / Injeção via Construtor
    public CustomUserDetailsService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    /**
     * Loads the user's details by username by calling the authentication service.
     * Carrega os detalhes do usuário pelo nome de usuário chamando o serviço de autenticação.
     *
     * @param username The username identifying the user whose data is required. / O nome de usuário que identifica o usuário cujos dados são necessários.
     * @return A fully populated UserDetails object (or null if the user is not found). / Um objeto UserDetails totalmente populado (ou nulo se o usuário não for encontrado).
     * @throws UsernameNotFoundException if the user could not be found or the auth service is unavailable. / se o usuário não pôde ser encontrado ou o serviço de autenticação está indisponível.
     */
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
            // Map the DTO to Spring Security's UserDetails
            // Mapeia o DTO para o UserDetails do Spring Security
            return new User(
                    userDetailsDTO.getUsername(),
                    // Password from DTO is usually hashed and not needed/used for JWT validation here
                    // A senha do DTO geralmente está hasheada e não é necessária/usada para validação JWT aqui
                    userDetailsDTO.getPassword() != null ? userDetailsDTO.getPassword() : "", // Provide empty string if null
                    // Map roles from DTO to GrantedAuthority objects
                    // Mapeia as roles do DTO para objetos GrantedAuthority
                    userDetailsDTO.getRoles() != null ?
                        userDetailsDTO.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())) // Prefix with ROLE_
                            .collect(Collectors.toList())
                        : Collections.emptyList() // Return empty list if roles are null
            );

        } catch (FeignException.NotFound e) {
            log.warn("User not found via auth service for username: {}. Feign status: 404", username);
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