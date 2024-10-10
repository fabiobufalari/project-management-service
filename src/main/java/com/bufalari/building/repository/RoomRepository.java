package com.bufalari.building.repository;

import com.bufalari.building.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    // Custom queries (if needed) can be added here
}
