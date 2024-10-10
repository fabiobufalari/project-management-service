package com.bufalari.building.repository;

import com.bufalari.building.entity.WallRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallRoomMappingRepository extends JpaRepository<WallRoomMapping, Long> {
}