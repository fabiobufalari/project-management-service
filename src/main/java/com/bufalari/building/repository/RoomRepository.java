package com.bufalari.building.repository;

import com.bufalari.building.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    // Método para buscar um ambiente pelo tipo e número do andar
    Optional<RoomEntity> findByRoomTypeAndFloorNumber(String roomType, int floorNumber);

    // Método para buscar um ambiente pelo UUID
    Optional<RoomEntity> findByUuid(UUID uuid);

}