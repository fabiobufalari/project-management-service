package com.bufalari.building.repository;

import com.bufalari.building.entity.ProjectEntity; // Importar
import com.bufalari.building.entity.WallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WallRepository extends JpaRepository<WallEntity, UUID> { // <<<--- Alterado para UUID

    // O método findByUuid(UUID uuid) é o mesmo que findById(UUID id) se 'id' for o campo UUID.
    // Se você tinha um campo 'uuid' separado do '@Id', então este método faria sentido.
    // Assumindo que 'id' é o campo UUID principal.
    // Optional<WallEntity> findByUuid(UUID uuid); // Redundante se 'id' é o UUID

    // Busca por ID textual (legado ou identificador de negócio)
    Optional<WallEntity> findByWallIdAndProject(String wallId, ProjectEntity project); // Adicionar projeto para unicidade

    // Busca paredes por projeto
    List<WallEntity> findByProject(ProjectEntity project);
    List<WallEntity> findByProject_Id(UUID projectId); // <<<--- Por ID do Projeto (UUID)

    // Busca paredes por número do andar e projeto
    List<WallEntity> findByFloorNumberAndProject(int floorNumber, ProjectEntity project);
}