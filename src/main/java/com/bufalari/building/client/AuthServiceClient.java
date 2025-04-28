package com.bufalari.building.client; // Pacote correto

import com.bufalari.building.DTO.UserDetailsDTO; // Pacote correto
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// <<< AJUSTE NO NOME (opcional) e URL via application.yml >>>
@FeignClient(name = "auth-service-client-project", url = "${auth.service.url}")
public interface AuthServiceClient {

    // <<< AJUSTE NO PATH >>>
    @GetMapping("/users/username/{username}") // Relativo a http://localhost:8083/api
    UserDetailsDTO getUserByUsername(@PathVariable("username") String username);
}