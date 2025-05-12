package com.bufalari.building.repository;

import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.enums.ProjectStatus; // Importar Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // Importar
import java.util.List; // Importar
import java.util.Optional; // Importar
import java.util.UUID;    // <<<--- IMPORT UUID

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> { // <<<--- Alterado para UUID
    // Métodos de consulta personalizados:
    Optional<ProjectEntity> findByProjectNameIgnoreCase(String projectName);
    List<ProjectEntity> findByStatus(ProjectStatus status);
    List<ProjectEntity> findByClientId(UUID clientId); // Buscar por ID do Cliente (UUID)
    List<ProjectEntity> findByStartDatePlannedBetween(LocalDate start, LocalDate end);
}