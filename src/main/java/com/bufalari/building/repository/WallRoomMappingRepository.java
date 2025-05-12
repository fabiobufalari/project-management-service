package com.bufalari.building.repository;

import com.bufalari.building.entity.RoomEntity; // Importar
import com.bufalari.building.entity.WallEntity; // Importar
import com.bufalari.building.entity.WallRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar
import java.util.Optional;
import java.util.UUID; // <<<--- IMPORT UUID

@Repository
public interface WallRoomMappingRepository extends JpaRepository<WallRoomMapping, UUID> { // <<<--- Alterado para UUID

    // Buscar mapeamentos por Parede
    List<WallRoomMapping> findByWall(WallEntity wall);
    List<WallRoomMapping> findByWall_Id(UUID wallId); // <<<--- Por ID da Parede (UUID)

    // Buscar mapeamentos por Cômodo
    List<WallRoomMapping> findByRoom(RoomEntity room);
    List<WallRoomMapping> findByRoom_Id(UUID roomId); // <<<--- Por ID do Cômodo (UUID)

    // Buscar um mapeamento específico
    Optional<WallRoomMapping> findByWallAndRoomAndSide(WallEntity wall, RoomEntity room, String side);
}