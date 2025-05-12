package com.bufalari.building.service;

import com.bufalari.building.converts.DoorConverter; // Importar para converter DoorDTO para DoorCalculationDTO
import com.bufalari.building.converts.WallConverter;
import com.bufalari.building.converts.WindowConverter; // Importar para converter WindowDTO para WindowCalculationDTO
import com.bufalari.building.entity.FloorEntity;
import com.bufalari.building.entity.ProjectEntity;
import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.entity.WallRoomMapping;
import com.bufalari.building.exception.ResourceNotFoundException;
import com.bufalari.building.repository.FloorRepository;
import com.bufalari.building.repository.ProjectRepository;
import com.bufalari.building.repository.RoomRepository; // Necessário se for criar/buscar cômodos
import com.bufalari.building.repository.WallRepository;
import com.bufalari.building.repository.WallRoomMappingRepository; // Necessário se for criar mapeamentos
import com.bufalari.building.requestDTO.RoomSideDTO;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.responseDTO.DoorCalculationDTO; // Importar
import com.bufalari.building.responseDTO.StudCalculationResultDTO;
import com.bufalari.building.responseDTO.StudDTO;
import com.bufalari.building.responseDTO.WallCalculationDTO;
import com.bufalari.building.responseDTO.WindowCalculationDTO; // Importar
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WallCalculationService {

    private static final Logger log = LoggerFactory.getLogger(WallCalculationService.class);

    // Repositórios e conversores para o caso de "opcionalmente criar"
    private final WallRepository wallRepository;
    private final ProjectRepository projectRepository;
    private final FloorRepository floorRepository;
    private final RoomRepository roomRepository;
    private final WallRoomMappingRepository wallRoomMappingRepository;
    private final WallConverter wallConverter;
    private final DoorConverter doorConverter;
    private final WindowConverter windowConverter;


    public double calculateLinearFootage(double totalLengthInFeet) {
        return totalLengthInFeet;
    }

    public double calculateSquareFootage(double totalLengthInFeet, double totalHeightInFeet) {
        return totalLengthInFeet * totalHeightInFeet;
    }

    public String determineMaterialType(WallEntity wall) {
        if (wall == null) {
            log.warn("Attempting to determine material type for a null wall.");
            return "Unknown";
        }
        if (WallEntity.TYPE_EXTERNAL.equalsIgnoreCase(wall.getType())) {
            return "Concrete"; // Ou outro material externo padrão
        } else {
            boolean isAdjacentToWetArea = false;
            if (wall.getRoomMappings() != null && !wall.getRoomMappings().isEmpty()) {
                isAdjacentToWetArea = wall.getRoomMappings().stream()
                                          .map(WallRoomMapping::getRoom)
                                          .filter(Objects::nonNull)
                                          .anyMatch(RoomEntity::isWetArea);
            } else {
                 // Se é interna mas não tem mapeamentos, pode ser um erro de modelagem ou
                 // uma parede que ainda não foi totalmente definida.
                 log.warn("Wall (Textual ID: {}, UUID: {}) is internal but has no room mappings. Assuming standard Drywall.",
                          wall.getWallId(), wall.getId());
            }
            return isAdjacentToWetArea ? "Moisture Resistant Drywall" : "Drywall";
        }
    }

    public StudCalculationResultDTO calculateStuds(WallEntity wall) {
        if (wall == null || wall.getStudSpacingInch() == null || wall.getStudSpacingInch() <= 0) {
            log.warn("Stud calculation cannot be performed: wall is null or stud spacing is invalid for Wall (Textual ID: {}).",
                     wall != null ? wall.getWallId() : "N/A");
            return new StudCalculationResultDTO(wall != null ? wall.getWallId() : "N/A", wall != null ? wall.getId() : null, 0, 0.0);
        }

        double totalLengthInInches = wall.getTotalLengthInFeet() * 12.0;
        int studCount = (int) Math.ceil(totalLengthInInches / wall.getStudSpacingInch()) + 1;

        // Considerar a altura das plates (soleiras/travessas superior e inferior)
        // Assumindo 1.5 polegadas por plate (madeira de 2x)
        double plateThicknessTotalInches = (wall.getNumberOfPlates() != null ? wall.getNumberOfPlates() : 3) * 1.5;
        double plateThicknessTotalFeet = plateThicknessTotalInches / 12.0;

        double studHeightFeet = wall.getTotalHeightInFeet() - plateThicknessTotalFeet;
        studHeightFeet = Math.max(0, studHeightFeet); // Garante que não seja negativo

        double studLinearFootage = studCount * studHeightFeet;

        log.debug("Studs calculation for Wall (Textual ID: '{}', UUID: {}): Count={}, LinearFootage={}",
                  wall.getWallId(), wall.getId(), studCount, studLinearFootage);
        return new StudCalculationResultDTO(wall.getWallId(), wall.getId(), studCount, studLinearFootage);
    }

    public WallEntity performBaseCalculationsAndUpdateEntity(WallEntity wallEntity) {
        if (wallEntity == null) {
            log.error("Attempted to perform calculations on a null WallEntity.");
            return null;
        }
        log.debug("Performing base calculations for Wall (Textual ID: '{}', UUID: {})", wallEntity.getWallId(), wallEntity.getId());

        wallEntity.setLinearFootage(calculateLinearFootage(wallEntity.getTotalLengthInFeet()));
        wallEntity.setSquareFootage(calculateSquareFootage(wallEntity.getTotalLengthInFeet(), wallEntity.getTotalHeightInFeet()));
        
        // O materialType depende dos RoomMappings, que são criados após a persistência inicial da WallEntity.
        // Portanto, o materialType será definido após os mapeamentos.
        // wallEntity.setMaterialType(determineMaterialType(wallEntity)); // Movido para após criação dos mappings

        StudCalculationResultDTO studResult = calculateStuds(wallEntity);
        wallEntity.setStudCount(studResult.getStudCount());
        wallEntity.setStudLinearFootage(studResult.getStudLinearFootage());

        log.info("Base calculations completed for Wall (Textual ID: '{}', UUID: {})", wallEntity.getWallId(), wallEntity.getId());
        return wallEntity;
    }

    @Transactional
    public List<WallCalculationDTO> processWallDTOs(List<WallDTO> wallDTOs, UUID projectId, UUID floorId) {
        if (wallDTOs == null || wallDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        ProjectEntity project = null;
        FloorEntity floor = null;
        boolean persist = false;

        if (projectId != null && floorId != null) {
            project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
            floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with ID: " + floorId));
            if (!floor.getProject().getId().equals(project.getId())) {
                throw new IllegalArgumentException("Floor ID " + floorId + " does not belong to Project ID " + projectId);
            }
            persist = true;
            log.info("Processing {} walls for persistence into Project {} / Floor {}", wallDTOs.size(), projectId, floorId);
        } else {
            log.info("Processing {} walls for calculation preview only.", wallDTOs.size());
        }

        List<WallCalculationDTO> results = new ArrayList<>();
        final ProjectEntity finalProject = project; // Para lambda
        final FloorEntity finalFloor = floor;       // Para lambda

        for (WallDTO wallDTO : wallDTOs) {
            WallEntity wallEntity = wallConverter.toEntity(wallDTO);
            if (finalProject != null) wallEntity.setProject(finalProject);
            wallEntity.setFloorNumber(finalFloor != null ? finalFloor.getFloorNumber() : wallDTO.getFloorNumber()); // Usa do DTO se não for persistir

            performBaseCalculationsAndUpdateEntity(wallEntity); // Cálculos básicos

            if (persist) {
                WallEntity savedWall = wallRepository.save(wallEntity);
                log.debug("Persisted Wall (Textual ID: {}) with UUID: {}", savedWall.getWallId(), savedWall.getId());

                // Criar RoomEntities e WallRoomMappings
                if (wallDTO.getRoomSides() != null) {
                    for (RoomSideDTO roomSideDTO : wallDTO.getRoomSides()) {
                        RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloor_FloorNumberAndProject(
                                roomSideDTO.getRoomType(), finalFloor.getFloorNumber(), finalProject)
                            .orElseGet(() -> {
                                RoomEntity newRoom = RoomEntity.builder()
                                        .roomType(roomSideDTO.getRoomType())
                                        .isWetArea(roomSideDTO.isWetArea())
                                        .floor(finalFloor)
                                        .project(finalProject)
                                        .build();
                                return roomRepository.save(newRoom);
                            });
                        WallRoomMapping mapping = WallRoomMapping.builder()
                                .wall(savedWall)
                                .room(roomEntity)
                                .side(roomSideDTO.getSideOfWall())
                                .build();
                        wallRoomMappingRepository.save(mapping);
                    }
                }
                // Recarregar a parede para obter os mapeamentos e definir o materialType
                WallEntity reloadedWall = wallRepository.findById(savedWall.getId()).orElse(savedWall);
                reloadedWall.setMaterialType(determineMaterialType(reloadedWall));
                wallEntity = wallRepository.save(reloadedWall); // Salva o materialType
            } else {
                // Se não persistir, o materialType é baseado apenas no DTO (pode ser menos preciso)
                // Para preview, o `determineMaterialType` pode não ter `roomMappings`.
                // A lógica de `determineMaterialType` já lida com `roomMappings` nulos.
                 wallEntity.setMaterialType(determineMaterialType(wallEntity));
            }
            results.add(convertToCalculationDTO(wallEntity));
        }
        return results;
    }

    // Método auxiliar para converter WallEntity para WallCalculationDTO
    public WallCalculationDTO convertToCalculationDTO(WallEntity entity) {
        if (entity == null) return null;
        WallCalculationDTO dto = new WallCalculationDTO();
        dto.setWallUuid(entity.getId()); // Pode ser nulo se a entidade não foi salva (preview)
        dto.setWallIdentification(entity.getWallId());
        dto.setDescription(entity.getDescription());
        dto.setSideOfWall(entity.getType()); // "external" ou "internal"
        dto.setTotalWallLengthFoot(entity.getTotalLengthInFeet());
        dto.setWallThicknessInch(entity.getWallThicknessInch());

        boolean isWet = false;
        if (entity.getRoomMappings() != null) {
            isWet = entity.getRoomMappings().stream().anyMatch(m -> m.getRoom() != null && m.getRoom().isWetArea());
        }
        dto.setWetArea(isWet);

        dto.setNumberOfPlates(entity.getNumberOfPlates() != null ? entity.getNumberOfPlates() : 0);
        // numberOfHeaders, numberOfPlywoodSheets, numberOfDrywallSheets precisam ser calculados
        dto.setNumberOfHeaders(0); // Placeholder
        dto.setNumberOfPlywoodSheets(0); // Placeholder
        dto.setNumberOfDrywallSheets(0); // Placeholder

        dto.setMaterialType(entity.getMaterialType());

        dto.setStuds(List.of(new StudDTO(
                entity.getStudCount() + "x Studs (" + entity.getStudLinearFootage() + " ft total)",
                entity.getStudCount()
        )));

        if (entity.getDoors() != null) {
            dto.setDoors(entity.getDoors().stream().map(doorEntity -> {
                DoorCalculationDTO doorCalcDto = new DoorCalculationDTO();
                doorCalcDto.setId(doorEntity.getId());
                doorCalcDto.setDoorId(doorEntity.getDoorId());
                doorCalcDto.setDoorWidthFoot(doorEntity.getWidthFoot() + doorEntity.getWidthInches()/12.0);
                doorCalcDto.setDoorHeightFoot(doorEntity.getHeightFoot() + doorEntity.getHeightInches()/12.0);
                doorCalcDto.setDoorThicknessInch(doorEntity.getThicknessInch());
                // doorCalcDto.setCodeReference(...); // Se houver
                return doorCalcDto;
            }).collect(Collectors.toList()));
        }

        if (entity.getWindows() != null) {
            dto.setWindows(entity.getWindows().stream().map(windowEntity -> {
                WindowCalculationDTO windowCalcDto = new WindowCalculationDTO();
                windowCalcDto.setId(windowEntity.getId());
                windowCalcDto.setWindowId(windowEntity.getWindowId());
                windowCalcDto.setWindowWidthFoot(windowEntity.getWidthFoot() + windowEntity.getWidthInches()/12.0);
                windowCalcDto.setWindowHeightFoot(windowEntity.getHeightFoot() + windowEntity.getHeightInches()/12.0);
                windowCalcDto.setWindowThicknessInch(windowEntity.getThicknessInch());
                // windowCalcDto.setCodeReference(...);
                return windowCalcDto;
            }).collect(Collectors.toList()));
        }
        // dto.setCodeReference(...); // Código de referência para a parede em si
        return dto;
    }

    @Transactional(readOnly = true)
    public WallDTO getWallDtoByUuid(UUID uuid) {
        log.debug("Fetching WallDTO by UUID: {}", uuid);
        return wallRepository.findById(uuid)
                .map(wallConverter::toDto) // Usa o WallConverter principal
                .orElseThrow(() -> new ResourceNotFoundException("Wall not found with UUID: " + uuid));
    }
}