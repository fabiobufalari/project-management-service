package com.bufalari.building.client;

import com.bufalari.building.DTO.UserDetailsDTO; // Usar DTO local
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for communication with the authentication service.
 * Cliente Feign para comunicação com o serviço de autenticação.
 */
@FeignClient(name = "auth-service-client-project", url = "${auth.service.url}")
public interface AuthServiceClient {

    /**
     * Retrieves user details by username.
     * O path deve corresponder ao endpoint no Auth Controller.
     * Ex: Se a URL base do Auth Service no application.yml for "http://localhost:8080/api/auth",
     * e o endpoint for "/users/username/{username}", então o @GetMapping deve ser "/users/username/{username}".
     */
    @GetMapping("/users/username/{username}") // <<< Ajuste conforme endpoint REAL no auth-service
    UserDetailsDTO getUserByUsername(@PathVariable("username") String username);

    /**
     * Retrieves user details by user ID (UUID).
     */
    @GetMapping("/users/{id}") // <<< Ajuste conforme endpoint REAL no auth-service
    UserDetailsDTO getUserById(@PathVariable("id") String userId); // Passa UUID como String
}