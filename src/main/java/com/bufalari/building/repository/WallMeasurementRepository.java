package com.bufalari.building.repository;

import com.bufalari.building.model.WallMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallMeasurementRepository extends JpaRepository<WallMeasurementEntity, Long> {
}