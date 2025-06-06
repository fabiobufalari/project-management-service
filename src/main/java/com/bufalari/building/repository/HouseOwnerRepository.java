package com.bufalari.building.repository;

import com.bufalari.building.entity.HouseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar
import java.util.UUID; // <<<--- IMPORT UUID

@Repository
public interface HouseOwnerRepository extends JpaRepository<HouseOwner, UUID> { // <<<--- Alterado para UUID
    // Métodos de consulta personalizados:
    List<HouseOwner> findByProjectId(UUID projectId); // Buscar por ID do Projeto (UUID)
    List<HouseOwner> findByClientId(UUID clientId);   // Buscar por ID do Cliente (UUID)
}