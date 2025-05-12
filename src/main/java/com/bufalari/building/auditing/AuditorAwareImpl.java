package com.bufalari.building.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
// import org.springframework.stereotype.Component; // Removido, será @Bean

import java.util.Optional;
// import java.util.UUID; // Import não usado

/**
 * Implementation of AuditorAware for the Project Management service.
 */
// @Component // Removido - será configurado como @Bean na JpaAuditingConfig
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.of("system_project_mgmt"); // <<<--- Usuário de sistema específico
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof User userPrincipal) { // Pattern matching
           username = userPrincipal.getUsername();
        } else if (principal instanceof String stringPrincipal) {
           username = stringPrincipal;
        } else {
             return Optional.of("unknown_project_user");
        }
        return Optional.of(username);
    }
}