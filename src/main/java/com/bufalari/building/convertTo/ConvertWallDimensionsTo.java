package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.WallDimensionsDTO;
import com.bufalari.building.enums.SideOfWall;
import com.bufalari.building.model.WallMeasurementEntity;
import com.bufalari.building.util.convert;
import org.springframework.stereotype.Component;

import static com.bufalari.building.util.convert.*;

@Component
public class ConvertWallDimensionsTo {

    public WallMeasurementEntity convertToEntity(WallDimensionsDTO dto) {
        WallMeasurementEntity entity = new WallMeasurementEntity();
        ConvertDoorDimensionsTo convertDoorDTOsToEntities = new ConvertDoorDimensionsTo();
        ConvertWindowDimensionsTo convertWindowDTOsToEntities = new ConvertWindowDimensionsTo();

        entity.setIdentifyWall(dto.getIdentifyWall());
        entity.setWallLengthFoot(dto.getWallLengthFoot() + convertInchesToFeet(convertStringToInches(dto.getWallLengthInches())));
        entity.setWallHeightFoot(dto.getWallHeightFoot() + convertInchesToFeet(convertStringToInches(dto.getWallHeightInches())));
        entity.setWallThickness(dto.getWallThickness());
        entity.setSideOfWall(SideOfWall.valueOf(dto.getSideOfWall()));
        entity.setStudSpacing(dto.getStudSpacing());
        entity.setDoors(convertDoorDTOsToEntities.convertDoorDTOsToEntities(dto.getDoor()));
        entity.setWindows(convertWindowDTOsToEntities.convertWindowDTOsToEntities(dto.getWindow()));

        return entity;
    }

    public WallDimensionsDTO convertToDTO(WallMeasurementEntity entity) {
        WallDimensionsDTO dto = new WallDimensionsDTO();
        ConvertDoorDimensionsTo convertDoorDimensionsTo = new ConvertDoorDimensionsTo();
        ConvertWindowDimensionsTo convertWindowDimensionsTo = new ConvertWindowDimensionsTo();

        dto.setIdentifyWall(entity.getIdentifyWall());
        dto.setWallLengthFoot(entity.getWallLengthFoot());
        //dto.setWallLengthInches(entity.getWallLengthInches());
        dto.setWallHeightFoot(entity.getWallHeightFoot());
        //dto.setWallHeightInches(entity.getWallHeightInches());
        dto.setWallThickness(entity.getWallThickness());
        dto.setSideOfWall(String.valueOf(entity.getSideOfWall()));
        dto.setStudSpacing(entity.getStudSpacing());
        dto.setDoor(convertDoorDimensionsTo.convertDoorEntitiesToDto(entity.getDoors()));
        dto.setWindow(convertWindowDimensionsTo.convertWindowEntitiesToDto(entity.getWindows()));

        return dto;
    }
}
