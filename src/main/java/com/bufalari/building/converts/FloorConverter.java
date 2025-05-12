package com.bufalari.building.converts;

import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.WallEntity; // Importar para exemplo
import com.bufalari.building.requestDTO.CalculationStructureDTO;
import com.bufalari.building.responseDTO.FloorResponseDTO; // Usar responseDTO para saída
import com.bufalari.building.requestDTO.WallDTO; // Usar para mapear paredes
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FloorConverter {

    private static final Logger log = LoggerFactory.getLogger(FloorConverter.class);
    private final WallConverter wallConverter; // Para converter WallEntity para WallDTO (de resposta)
    // Injetar outros conversores de componentes (CeilingConverter, etc.) se FloorResponseDTO os incluir

    public FloorEntity requestDtoToEntity(CalculationStructureDTO dto, ProjectEntity project) {
        if (dto == null || project == null) {
            log.warn("CalculationStructureDTO ou ProjectEntity nulo, não é possível converter para FloorEntity.");
            return null;
        }
        log.debug("Convertendo CalculationStructureDTO para FloorEntity, andar: {}, projeto: {}", dto.getFloorNumber(), project.getId());
        FloorEntity entity = new FloorEntity();
        entity.setId(dto.getFloorUuid()); // Se presente no DTO (para atualização)
        entity.setFloorIdLegacy(dto.getFloorId()); // ID legado, se houver
        entity.setFloorNumber(dto.getFloorNumber());
        entity.setAreaSquareFeet(dto.getAreaSquareFeet());
        entity.setHeated(dto.isHeated());
        entity.setMaterial(dto.getMaterial());
        entity.setProject(project);

        // A lista de Rooms (e indiretamente Walls) será criada e associada pelo FloorService/RoomService/WallService.
        entity.setRooms(new ArrayList<>());

        // Se FloorEntity tivesse campos diretos para Ceiling, Baseboard, etc.:
        // if (dto.getCeiling() != null) entity.setCeiling(ceilingConverter.toEntity(dto.getCeiling()));
        // ...

        return entity;
    }

    public FloorResponseDTO entityToDetailResponseDto(FloorEntity entity) {
        if (entity == null) {
            return null;
        }
        FloorResponseDTO.FloorResponseDTOBuilder builder = FloorResponseDTO.builder()
                .floorUuid(entity.getId())
                .floorIdLegacy(entity.getFloorIdLegacy())
                .floorNumber(entity.getFloorNumber())
                .areaSquareFeet(entity.getAreaSquareFeet())
                .heated(entity.isHeated())
                .material(entity.getMaterial());

        // Mapear paredes associadas aos cômodos deste andar
        if (entity.getRooms() != null) {
            List<WallDTO> wallDTOs = entity.getRooms().stream()
                .flatMap(room -> room.getWallMappings().stream())
                .map(wallMapping -> wallConverter.toDto(wallMapping.getWall())) // WallConverter.toDto(WallEntity)
                .distinct() // Evita paredes duplicadas se uma parede servir a múltiplos cômodos
                .collect(Collectors.toList());
            builder.walls(wallDTOs);
        } else {
            builder.walls(Collections.emptyList());
        }

        // Mapear outros componentes (Ceiling, Baseboard, etc.) para seus DTOs de resposta
        // if (entity.getCeiling() != null) builder.ceiling(ceilingConverter.toDto(entity.getCeiling()));
        // ...

        return builder.build();
    }

    // Método para mapear FloorEntity para CalculationStructureDTO (se necessário para update ou GET complexo)
    public CalculationStructureDTO entityToCalculationStructureDto(FloorEntity entity) {
        if (entity == null) return null;
        CalculationStructureDTO dto = new CalculationStructureDTO();
        dto.setFloorUuid(entity.getId());
        dto.setFloorId(entity.getFloorIdLegacy());
        dto.setFloorNumber(entity.getFloorNumber());
        dto.setAreaSquareFeet(entity.getAreaSquareFeet());
        dto.setHeated(entity.isHeated());
        dto.setMaterial(entity.getMaterial());

        if (entity.getRooms() != null) {
            List<WallDTO> wallDTOs = entity.getRooms().stream()
                .flatMap(room -> room.getWallMappings().stream())
                .map(wallMapping -> wallConverter.toDto(wallMapping.getWall()))
                .distinct()
                .collect(Collectors.toList());
            dto.setWalls(wallDTOs);
        } else {
            dto.setWalls(new ArrayList<>());
        }
        // Mapear outros componentes (CeilingDTO, etc.)
        // if (entity.getCeiling() != null) dto.setCeiling(ceilingConverter.toDto(entity.getCeiling()));

        return dto;
    }
}