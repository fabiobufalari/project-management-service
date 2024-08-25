package com.bufalari.building.repository;

import com.bufalari.building.model.DoorDimensionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoorDimensionsRepository extends JpaRepository<DoorDimensionsEntity, Long> {
}