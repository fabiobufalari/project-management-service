// Path: project-management-service/src/main/java/com/bufalari/building/dto/UserDetailsDTO.java
package com.bufalari.building.DTO;

import lombok.Data;
import lombok.NoArgsConstructor; // Add default constructor for Jackson/Feign
import lombok.AllArgsConstructor; // Add all-args constructor

import java.util.List;

/**
 * DTO for user details received from the authentication service.
 * DTO para os detalhes do usuário recebidos do serviço de autenticação.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private String username;
    private String password; // Usually received hashed, not used directly here / Geralmente recebida com hash, não usada diretamente aqui
    private List<String> roles;
}