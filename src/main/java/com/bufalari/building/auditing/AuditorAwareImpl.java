package com.bufalari.building.auditing; // <<<--- Pacote correto para este serviço

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User; // Pode ser necessário ajustar se usar outra classe principal
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID; // Importar UUID se for usar como ID

/**
 * Implementation of AuditorAware to provide the current user's identifier for the Building service.
 * Implementação de AuditorAware para fornecer o identificador do usuário atual para o serviço Building.
 */

public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the identifier (e.g., username) of the current auditor.
     * Retorna o identificador (ex: username) do auditor atual.
     *
     * @return Optional containing the current auditor's identifier, or "system" if none is available.
     *         Optional contendo o identificador do auditor atual, ou "system" se nenhum estiver disponível.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // Handle cases where there is no authenticated user (e.g., system processes, tests)
            // Trata casos onde não há usuário autenticado (ex: processos do sistema, testes)
            return Optional.of("system_building"); // Use um identificador específico ou "system"
        }

        // Assuming the principal is the username string or UserDetails
        // Assumindo que o principal é a string do nome de usuário ou UserDetails
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof User) {
           username = ((User) principal).getUsername();
        // } else if (principal instanceof YourCustomUserDetails) { // Se você tiver um UserDetails customizado
        //    username = ((YourCustomUserDetails) principal).getUsername(); // ou .getId().toString();
        } else if (principal instanceof String) {
           username = (String) principal;
        } else {
            // Fallback if principal type is unexpected
            // Fallback se o tipo do principal for inesperado
             return Optional.of("unknown_user");
        }

        // Retorna o username como identificador do auditor
        // Returns the username as the auditor identifier
        return Optional.of(username);
    }
}