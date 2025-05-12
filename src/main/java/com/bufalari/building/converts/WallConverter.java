package com.bufalari.building.converts;

import com.bufalari.building.entity.DoorEntity; // Mantido
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.entity.WindowEntity; // Mantido
import com.bufalari.building.requestDTO.DoorDTO; // Usar requestDTO
import com.bufalari.building.requestDTO.RoomSideDTO; // Usar requestDTO.RoomSideDTO
import com.bufalari.building.requestDTO.WallDTO; // Usar requestDTO.WallDTO
import com.bufalari.building.requestDTO.WindowDTO; // Usar requestDTO
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
// import java.util.List; // Não é mais necessário se as listas são inicializadas
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WallConverter {

    private final WindowConverter windowConverter; // Assume que WindowConverter retorna requestDTO.WindowDTO
    private final DoorConverter doorConverter;   // Assume que DoorConverter retorna requestDTO.DoorDTO

    public WallEntity toEntity(WallDTO dto) { // Renomeado de requestDtoToEntity para clareza
        if (dto == null) return null;

        WallEntity entity = new WallEntity();
        // ID da entidade será definido ao salvar ou se dto.getWallUuid() for usado para update
        entity.setId(dto.getWallUuid());
        entity.setWallId(dto.getWallId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setLengthFoot(dto.getLengthFoot());
        entity.setLengthInches(dto.getLengthInches());
        entity.setHeightFoot(dto.getHeightFoot());
        entity.setHeightInches(dto.getHeightInches());
        entity.setWallThicknessInch(dto.getWallThicknessInch());
        entity.setFloorNumber(dto.getFloorNumber());
        entity.setNumberOfPlates(dto.getNumberOfPlates() != null ? dto.getNumberOfPlates() : 3); // Default se nulo
        entity.setStudSpacingInch(dto.getStudSpacingInch());
        // Project será associado no serviço
        // RoomMappings serão criados no serviço

        if (dto.getWindows() != null) {
            entity.setWindows(dto.getWindows().stream()
                .map(windowConverter::toEntity) // WindowConverter.toEntity(WindowDTO)
                .collect(Collectors.toList()));
        } else {
            entity.setWindows(new ArrayList<>());
        }
        if (dto.getDoors() != null) {
            entity.setDoors(dto.getDoors().stream()
                .map(doorConverter::toEntity) // DoorConverter.toEntity(DoorDTO)
                .collect(Collectors.toList()));
        } else {
            entity.setDoors(new ArrayList<>());
        }
        return entity;
    }

    public WallDTO toDto(WallEntity entity) { // Renomeado de entityToResponseDto
        if (entity == null) return null;
        WallDTO dto = new WallDTO();
        dto.setWallUuid(entity.getId()); // ID da entidade
        dto.setWallId(entity.getWallId());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setLengthFoot(entity.getLengthFoot());
        dto.setLengthInches(entity.getLengthInches());
        dto.setHeightFoot(entity.getHeightFoot());
        dto.setHeightInches(entity.getHeightInches());
        dto.setWallThicknessInch(entity.getWallThicknessInch());
        dto.setFloorNumber(entity.getFloorNumber());
        dto.setNumberOfPlates(entity.getNumberOfPlates());
        dto.setStudSpacingInch(entity.getStudSpacingInch());
        dto.setMaterialType(entity.getMaterialType());

        if (entity.getWindows() != null) {
            dto.setWindows(entity.getWindows().stream()
                .map(windowConverter::toDto) // WindowConverter.toDto(WindowEntity)
                .collect(Collectors.toList()));
        }
        if (entity.getDoors() != null) {
            dto.setDoors(entity.getDoors().stream()
                .map(doorConverter::toDto) // DoorConverter.toDto(DoorEntity)
                .collect(Collectors.toList()));
        }

        if (entity.getRoomMappings() != null) {
            dto.setRoomSides(entity.getRoomMappings().stream()
                .map(mapping -> new RoomSideDTO( // Mapeando para requestDTO.RoomSideDTO
                        mapping.getRoom().getRoomType(), // roomName (seria roomType)
                        mapping.getRoom().isWetArea(),
                        mapping.getSide(),
                        mapping.getRoom().getRoomType() // roomType
                ))
                .collect(Collectors.toList()));
        }
        return dto;
    }
}