package com.bufalari.building.service;

import com.bufalari.building.converts.WallConverter;
import com.bufalari.building.entity.*;
import com.bufalari.building.exception.ResourceNotFoundException;
import com.bufalari.building.repository.RoomRepository;
import com.bufalari.building.repository.WallRepository;
import com.bufalari.building.repository.WallRoomMappingRepository;
import com.bufalari.building.requestDTO.RoomSideDTO;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.responseDTO.WallCalculationDTO; // Para getWallCalculationDtoByUuid
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class WallServiceImpl implements WallService {

    private static final Logger log = LoggerFactory.getLogger(WallServiceImpl.class);

    private final WallRepository wallRepository;
    private final WallConverter wallConverter;
    private final RoomRepository roomRepository;
    private final WallRoomMappingRepository wallRoomMappingRepository;
    private final WallCalculationService wallCalculationService;

    @Override
    public WallEntity createWallFromDTO(WallDTO wallDTO, FloorEntity floor, ProjectEntity project) {
        log.info("Creating wall '{}' for floor #{} of project {}", wallDTO.getWallId(), floor.getFloorNumber(), project.getId());

        WallEntity wallEntity = wallConverter.toEntity(wallDTO);
        wallEntity.setFloorNumber(floor.getFloorNumber());
        wallEntity.setProject(project);

        // Chamada corrigida para o método em WallCalculationService
        wallCalculationService.performBaseCalculationsAndUpdateEntity(wallEntity);

        WallEntity savedWall = wallRepository.save(wallEntity);
        log.debug("WallEntity (WallId: {}) persisted with UUID: {}", savedWall.getWallId(), savedWall.getId());

        if (wallDTO.getRoomSides() != null) {
            for (RoomSideDTO roomSideDTO : wallDTO.getRoomSides()) {
                String roomType = roomSideDTO.getRoomType();

                RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloor_FloorNumberAndProject(roomType, floor.getFloorNumber(), project)
                    .orElseGet(() -> {
                        log.debug("Creating new RoomEntity: Type '{}', Floor No. {}, Project UUID {}",
                                  roomType, floor.getFloorNumber(), project.getId());
                        RoomEntity newRoom = RoomEntity.builder()
                                .roomType(roomType)
                                .isWetArea(roomSideDTO.isWetArea())
                                .floor(floor)
                                .project(project) // Garante que o projeto está associado
                                .build();
                        return roomRepository.save(newRoom);
                    });

                WallRoomMapping mapping = WallRoomMapping.builder()
                        .wall(savedWall)
                        .room(roomEntity)
                        .side(roomSideDTO.getSideOfWall())
                        .build();
                wallRoomMappingRepository.save(mapping);
                log.debug("WallRoomMapping created for Wall UUID {} and Room UUID {}", savedWall.getId(), roomEntity.getId());
            }
        }

        // Recarregar a parede para garantir que os mapeamentos estejam presentes para determinar o materialType
        WallEntity reloadedWall = wallRepository.findById(savedWall.getId()).orElse(savedWall); // Fallback para savedWall se não encontrar (não deve acontecer)
        String materialType = wallCalculationService.determineMaterialType(reloadedWall);
        if (!Objects.equals(reloadedWall.getMaterialType(), materialType)) {
            reloadedWall.setMaterialType(materialType);
            savedWall = wallRepository.save(reloadedWall);
            log.debug("MaterialType of Wall UUID {} updated to '{}'", savedWall.getId(), materialType);
        } else {
             savedWall = reloadedWall; // Garante que a entidade retornada está atualizada
        }

        return savedWall;
    }

    @Override
    @Transactional(readOnly = true)
    public WallCalculationDTO getWallCalculationDtoByUuid(UUID wallUuid) {
        WallEntity wallEntity = wallRepository.findById(wallUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Wall not found with UUID: " + wallUuid));
        // O WallCalculationService agora tem um método para converter
        return wallCalculationService.convertToCalculationDTO(wallEntity); // Utiliza o método de conversão do WallCalculationService
    }

    @Override
    @Transactional(readOnly = true)
    public WallDTO getWallDtoByUuid(UUID wallUuid) {
        return wallRepository.findById(wallUuid)
                .map(wallConverter::toDto) // wallConverter.toDto retorna requestDTO.WallDTO
                .orElseThrow(() -> new ResourceNotFoundException("Wall not found with UUID: " + wallUuid));
    }
}