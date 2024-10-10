package com.bufalari.building.converts;

import javax.xml.stream.Location;

import com.bufalari.building.entity.*;
import com.bufalari.building.requestDTO.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    public ProjectEntity toEntity(ProjectInfoDTO dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectName(dto.getProjectName());
        entity.setDateTime(dto.getDateTime());
        entity.setBuildingType(dto.getBuildingType());
        entity.setNumberOfFloors(dto.getNumberOfFloors());
        entity.setHasBasement(dto.isHasBasement());

        // Converte a localização
        entity.setLocation(convertLocation(dto.getLocation()));

        // Converte os andares (floors)
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

        // Converte as paredes (walls)
        List<WallEntity> walls = dto.getWalls().stream()
                .map(this::convertWall)
                .collect(Collectors.toList());
        entity.setWalls(walls);

        return entity;
    }

    private WallEntity convertWall(WallDTO dto) {
        WallEntity entity = new WallEntity();
        entity.setWallId(dto.getWallId());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setLengthFoot(dto.getLengthFoot());
        entity.setLengthInches(dto.getLengthInches());
        entity.setHeightFoot(dto.getHeightFoot());
        entity.setHeightInches(dto.getHeightInches());
        entity.setWallThicknessInch(dto.getWallThicknessInch());
        entity.setMaterial(dto.getMaterial());

        // Converte as janelas
        if (dto.getWindows() != null) {
            List<WindowEntity> windows = dto.getWindows().stream()
                    .map(this::convertWindow)
                    .collect(Collectors.toList());
            entity.setWindows(windows);
        }

        // Converte as portas
        if (dto.getDoors() != null) {
            List<DoorEntity> doors = dto.getDoors().stream()
                    .map(this::convertDoor)
                    .collect(Collectors.toList());
            entity.setDoors(doors);
        }

        return entity;
    }

    private WindowEntity convertWindow(WindowDTO dto) {
        WindowEntity entity = new WindowEntity();
        entity.setWindowId(dto.getWindowId());
        entity.setWidthFoot(dto.getDimensions().getWidthFoot());
        entity.setWidthInches(dto.getDimensions().getWidthInches());
        entity.setHeightFoot(dto.getDimensions().getHeightFoot());
        entity.setHeightInches(dto.getDimensions().getHeightInches());
        entity.setThicknessInch(dto.getDimensions().getThicknessInch());
        return entity;
    }

    private DoorEntity convertDoor(DoorDTO dto) {
        DoorEntity entity = new DoorEntity();
        entity.setDoorId(dto.getDoorId());
        entity.setWidthFoot(dto.getDimensions().getWidthFoot());
        entity.setWidthInches(dto.getDimensions().getWidthInches());
        entity.setHeightFoot(dto.getDimensions().getHeightFoot());
        entity.setHeightInches(dto.getDimensions().getHeightInches());
        entity.setThicknessInch(dto.getDimensions().getThicknessInch());
        return entity;
    }

    private Location convertLocation(LocationDTO dto) {
        return new Location(
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getPostalCode()
        );
    }
}
