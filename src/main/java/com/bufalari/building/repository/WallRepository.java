package com.bufalari.building.repository;

import com.bufalari.building.entity.WallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallRepository extends JpaRepository<WallEntity, Long> {
}