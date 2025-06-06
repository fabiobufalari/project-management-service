package com.bufalari.building.repository;

import com.bufalari.building.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar
import java.util.UUID;    // <<<--- IMPORT UUID

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> { // <<<--- Alterado para UUID
    // Aqui você pode adicionar métodos customizados, como buscar por nome do cliente
    // Exemplo:
    // Optional<ClientEntity> findByClientNameIgnoreCase(String clientName);
}