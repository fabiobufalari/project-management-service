package com.bufalari.building.repository;

import com.bufalari.building.entity.FloorEntity; // Importar
import com.bufalari.building.entity.ProjectEntity; // Importar
import com.bufalari.building.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> { // <<<--- Alterado para UUID

    /**
     * Busca um cômodo pelo tipo, número do andar E PELO PROJETO.
     * É importante incluir o projeto para garantir unicidade do cômodo se roomType + floorNumber não forem únicos globalmente.
     */
    Optional<RoomEntity> findByRoomTypeAndFloor_FloorNumberAndProject(String roomType, int floorNumber, ProjectEntity project);
    // Se FloorEntity não for usado para derivar floorNumber, e floorNumber estiver direto em RoomEntity:
    // Optional<RoomEntity> findByRoomTypeAndFloorNumberAndProject(String roomType, int floorNumber, ProjectEntity project);


    // O método findByUuid(UUID uuid) já é fornecido pelo JpaRepository se o ID for UUID,
    // então um método customizado com este nome não é estritamente necessário a menos que
    // haja um campo 'uuid' separado do campo '@Id'.
    // Se 'id' é o campo UUID, então JpaRepository.findById(UUID id) é o método a ser usado.
    // Se você tem um campo `private UUID uuid;` separado do `@Id private UUID id;`,
    // então o método abaixo faz sentido. Pela entidade RoomEntity revisada, 'id' é o UUID.
    // Optional<RoomEntity> findByUuid(UUID uuid);

    // Buscar cômodos por andar
    List<RoomEntity> findByFloor(FloorEntity floor);
    List<RoomEntity> findByFloor_Id(UUID floorId); // <<<--- Por ID do andar (UUID)

    // Buscar cômodos por projeto
    List<RoomEntity> findByProject(ProjectEntity project);
    List<RoomEntity> findByProject_Id(UUID projectId); // <<<--- Por ID do projeto (UUID)
}