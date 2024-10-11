package com.bufalari.building.converts;

import com.bufalari.building.entity.*;
import com.bufalari.building.repository.RoomRepository;
import com.bufalari.building.repository.WallRepository;
import com.bufalari.building.repository.WallRoomMappingRepository;
import com.bufalari.building.requestDTO.*;
import com.bufalari.building.service.WallCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    @Autowired
    private WallCalculationService wallCalculationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WallRoomMappingRepository wallRoomMappingRepository;

    @Autowired
    private WallRepository wallRepository;


    public WallEntity convertWall(WallDTO dto, int floorNumber) {
        WallEntity entity = new WallEntity();
        entity.setWallId(dto.getWallId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setLengthFoot(dto.getLengthFoot());
        entity.setLengthInches(dto.getLengthInches());
        entity.setHeightFoot(dto.getHeightFoot());
        entity.setHeightInches(dto.getHeightInches());
        entity.setWallThicknessInch(dto.getWallThicknessInch());
        entity.setFloorNumber(floorNumber);

        // Calcula as medidas da parede
        entity.setLinearFootage(wallCalculationService.calculateLinearFootage(entity.getTotalLengthInFeet()));
        entity.setSquareFootage(wallCalculationService.calculateSquareFootage(entity.getTotalLengthInFeet(), entity.getTotalHeightInFeet()));

        // Persistir a WallEntity primeiro
        entity = wallRepository.save(entity); // Salvar a entidade antes de associá-la aos ambientes

        // Relacionar a parede com os ambientes (usando WallRoomMapping) e determinar o tipo de material
        for (RoomSideDTO roomSideDTO : dto.getRoomSides()) {
            RoomEntity roomEntity = roomRepository.findByRoomTypeAndFloorNumber(
                    roomSideDTO.getRoomType(), floorNumber).orElse(null);

            if (roomEntity != null) {
                WallRoomMapping mapping = new WallRoomMapping();
                mapping.setWall(entity);
                mapping.setRoom(roomEntity);
                mapping.setSide(roomSideDTO.getSideOfWall());

                // Adicionar a parede à lista de paredes do ambiente (bidirecional)
                roomEntity.getWalls().add(entity);
                entity.getRooms().add(roomEntity);

                wallRoomMappingRepository.save(mapping);

                // ... (determinar o materialType) ...
            }
        }

        return entity;
    }


    public ProjectEntity toEntity(ProjectInfoDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectName(dto.getProjectName());
        entity.setDateTime(LocalDateTime.parse(dto.getDateTime()));
        entity.setBuildingType(dto.getBuildingType());
        entity.setNumberOfFloors(dto.getNumberOfFloors());
        entity.setHasBasement(dto.isHasBasement());

        // Converter a localização usando o novo método convertLocationEntity
        entity.setLocationEntity(convertLocationEntity(dto.getLocation()));

        // Converter e definir os andares do projeto usando o novo método convertFloor
        List<FloorEntity> floors = dto.getCalculationStructure().stream()
                .map(this::convertFloor)
                .collect(Collectors.toList());
        entity.setFloors(floors);

        return entity;
    }

    private FloorEntity convertFloor(CalculationStructureDTO dto) {
        FloorEntity entity = new FloorEntity();
        entity.setFloorId(dto.getFloorId());
        entity.setFloorNumber(dto.getFloorNumber());
        entity.setAreaSquareFeet(dto.getAreaSquareFeet());
        entity.setHeated(dto.isHeated());
        entity.setMaterial(dto.getMaterial());
        entity.setWetArea(dto.isWetArea());

        // Converter as paredes (walls)
        List<WallEntity> walls = dto.getWalls().stream()
                .map(wallDTO -> convertWall(wallDTO, dto.getFloorNumber())) // Passar floorNumber para convertWall
                .collect(Collectors.toList());
        entity.setWalls(walls);

        // ... (Converter outros elementos: ceiling, baseboards, etc. - LEMBRE-SE de implementar esses métodos!) ...

        return entity;
    }

    // Método para converter LocationDTO em LocationEntity
    private LocationEntity convertLocationEntity(LocationDTO dto) {
        return new LocationEntity(
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getPostalCode()
        );
    }

}