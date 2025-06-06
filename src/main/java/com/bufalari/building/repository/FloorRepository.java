package com.bufalari.building.repository;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FloorRepository extends JpaRepository<FloorEntity, UUID> {

    List<FloorEntity> findByProject(ProjectEntity project);

    List<FloorEntity> findByProject_Id(UUID projectId);

    // Ajustado para buscar por floorNumber e Project (entidade)
    Optional<FloorEntity> findByFloorNumberAndProject(int floorNumber, ProjectEntity project);

    Optional<FloorEntity> findByFloorIdLegacyAndProject_Id(Long floorIdLegacy, UUID projectId);

    boolean existsByFloorNumberAndProject_Id(int floorNumber, UUID projectId);
}