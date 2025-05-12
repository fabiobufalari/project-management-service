package com.bufalari.building.service;

import com.bufalari.building.converts.RoomConverter;
import com.bufalari.building.converts.WallConverter;
import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallRoomMapping; // Importar
import com.bufalari.building.exception.ResourceNotFoundException;
import com.bufalari.building.exception.OperationNotAllowedException; // Para deleção
import com.bufalari.building.repository.FloorRepository;
import com.bufalari.building.repository.ProjectRepository;
import com.bufalari.building.repository.RoomRepository;
import com.bufalari.building.repository.WallRoomMappingRepository; // Importar para verificar dependências
import com.bufalari.building.requestDTO.RoomDTO;
import com.bufalari.building.requestDTO.WallDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections; // Para lista vazia
import java.util.List;
import java.util.Objects; // Importar
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;
    private final FloorRepository floorRepository;
    private final ProjectRepository projectRepository;
    private final WallRoomMappingRepository wallRoomMappingRepository; // Para verificar se há paredes associadas
    private final RoomConverter roomConverter;
    private final WallConverter wallConverter;

    public RoomDTO createRoom(RoomDTO roomDTO) {
        log.info("Creating room type '{}' for floor ID {} and project ID {}",
                 roomDTO.getRoomType(), roomDTO.getFloorId(), roomDTO.getProjectId());
        if (roomDTO.getId() != null) {
            log.warn("ID provided for room creation will be ignored.");
            roomDTO.setId(null);
        }

        ProjectEntity project = projectRepository.findById(roomDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + roomDTO.getProjectId()));

        FloorEntity floor = floorRepository.findById(roomDTO.getFloorId())
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with ID: " + roomDTO.getFloorId()));

        if (!floor.getProject().getId().equals(project.getId())) {
            throw new IllegalArgumentException("Floor ID " + floor.getId() + " does not belong to Project ID " + project.getId());
        }
        // Verificar se já existe um cômodo com o mesmo tipo, no mesmo andar e projeto
        roomRepository.findByRoomTypeAndFloor_FloorNumberAndProject(roomDTO.getRoomType(), floor.getFloorNumber(), project)
                .ifPresent(existingRoom -> {
                    throw new OperationNotAllowedException(
                        "Room with type '" + roomDTO.getRoomType() + "' already exists on floor " +
                        floor.getFloorNumber() + " for project " + project.getProjectName());
                });


        RoomEntity roomEntity = roomConverter.toEntity(roomDTO, floor, project);
        RoomEntity savedRoom = roomRepository.save(roomEntity);
        log.info("Room created successfully with ID: {}", savedRoom.getId());
        return roomConverter.toDto(savedRoom);
    }

    @Transactional(readOnly = true)
    public RoomDTO getRoomDtoById(UUID roomId) {
        log.debug("Fetching room by ID: {}", roomId);
        return roomRepository.findById(roomId)
                .map(roomConverter::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
    }

    @Transactional(readOnly = true)
    public List<WallDTO> getWallsForRoom(UUID roomId) {
        log.debug("Fetching walls for room ID: {}", roomId);
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

        if (room.getWallMappings() == null || room.getWallMappings().isEmpty()) {
            return Collections.emptyList();
        }

        return room.getWallMappings().stream()
                .map(WallRoomMapping::getWall)
                .filter(Objects::nonNull) // Garante que a parede no mapeamento não seja nula
                .map(wallConverter::toDto) // Assume que WallConverter.toDto(WallEntity) existe
                .collect(Collectors.toList());
    }

    public RoomDTO updateRoom(UUID roomId, RoomDTO roomDTO) {
        log.info("Updating room with ID: {}", roomId);
        RoomEntity existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

        // Validações e lógica de atualização
        // Não permitir alterar projeto ou andar de um cômodo existente (geralmente)
        if (!existingRoom.getProject().getId().equals(roomDTO.getProjectId())) {
            log.warn("Attempt to change project for room {} denied.", roomId);
            throw new IllegalArgumentException("Project of a room cannot be changed.");
        }
        if (!existingRoom.getFloor().getId().equals(roomDTO.getFloorId())) {
            log.warn("Attempt to change floor for room {} denied.", roomId);
            throw new IllegalArgumentException("Floor of a room cannot be changed.");
        }

        // Verificar se o novo roomType já existe neste andar e projeto (se for diferente do original)
        if (!existingRoom.getRoomType().equalsIgnoreCase(roomDTO.getRoomType())) {
             roomRepository.findByRoomTypeAndFloor_FloorNumberAndProject(
                            roomDTO.getRoomType(), existingRoom.getFloor().getFloorNumber(), existingRoom.getProject())
                .ifPresent(collidingRoom -> {
                    if (!collidingRoom.getId().equals(existingRoom.getId())) { // Se não for o mesmo cômodo
                        throw new OperationNotAllowedException(
                            "Room with type '" + roomDTO.getRoomType() + "' already exists on this floor for this project.");
                    }
                });
            existingRoom.setRoomType(roomDTO.getRoomType());
        }

        existingRoom.setWetArea(roomDTO.isWetArea());
        // Outros campos atualizáveis

        RoomEntity updatedRoom = roomRepository.save(existingRoom);
        log.info("Room with ID {} updated successfully.", roomId);
        return roomConverter.toDto(updatedRoom);
    }

    public void deleteRoom(UUID roomId) {
        log.info("Attempting to delete room with ID: {}", roomId);
        RoomEntity roomToDelete = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

        // Verificar se o cômodo tem paredes associadas através de WallRoomMapping
        if (wallRoomMappingRepository.findByRoom(roomToDelete) != null && !wallRoomMappingRepository.findByRoom(roomToDelete).isEmpty()) {
            log.warn("Deletion failed for room ID {}: Room has associated walls.", roomId);
            throw new OperationNotAllowedException("Cannot delete room: It has associated walls. Please remove wall associations first.");
        }

        // Adicionar outras verificações de dependência se necessário

        roomRepository.delete(roomToDelete); // Ou roomRepository.deleteById(roomId);
        log.info("Room with ID {} deleted successfully.", roomId);
    }
}