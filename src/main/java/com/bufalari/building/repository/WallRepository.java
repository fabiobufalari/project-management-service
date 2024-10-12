package com.bufalari.building.repository;

import com.bufalari.building.entity.WallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WallRepository extends JpaRepository<WallEntity, Long> {
    // Método para buscar uma parede pelo UUID
    Optional<WallEntity> findByUuid(UUID uuid);

    Optional<WallEntity> findByWallId(String wallId);

}