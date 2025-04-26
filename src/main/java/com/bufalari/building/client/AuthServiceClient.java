// Path: project-management-service/src/main/java/com/bufalari/building/client/AuthServiceClient.java
package com.bufalari.building.client;

import com.bufalari.building.DTO.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for communication with the authentication service.
 * Cliente Feign para comunicação com o serviço de autenticação.
 */
// The 'name' can be arbitrary if url is specified. The url points to the auth service.
// O 'name' pode ser arbitrário se a url for especificada. A url aponta para o serviço de autenticação.
@FeignClient(name = "auth-service-client", url = "${auth.service.url}") // Use property from application.yml
public interface AuthServiceClient {

    /**
     * Retrieves user details by username from the authentication service.
     * Busca os detalhes do usuário por nome de usuário no serviço de autenticação.
     *
     * @param username The username to search for. / O nome de usuário a ser buscado.
     * @return UserDetailsDTO containing user information. / UserDetailsDTO contendo informações do usuário.
     */
    // Ensure this endpoint matches the one exposed by your authentication service
    // Garanta que este endpoint corresponda ao exposto pelo seu serviço de autenticação
    @GetMapping("/api/users/username/{username}") // Assuming this is the correct endpoint in auth-service User Controller
    UserDetailsDTO getUserByUsername(@PathVariable("username") String username);
}