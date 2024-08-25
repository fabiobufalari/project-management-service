package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.DoorDimensionsDTO;
import com.bufalari.building.DTO.WindowDimensionsDTO;
import com.bufalari.building.model.DoorDimensionsEntity;
import com.bufalari.building.model.WindowDimensionsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertDoorDimensionsTo {

    public List<DoorDimensionsEntity> convertDoorDTOsToEntities(List<DoorDimensionsDTO> dtoListDoors) {
        return dtoListDoors.stream().map(dto -> {
            DoorDimensionsEntity entity = new DoorDimensionsEntity();
            entity.setDoorHeight(dto.getDoorHeight());
            entity.setDoorWidth(dto.getDoorWidth());
            entity.setDoorThickness(dto.getDoorThickness());

            return entity;
        }).collect(Collectors.toList());
    }

    public List<DoorDimensionsDTO> convertDoorEntitiesToDto(List<DoorDimensionsEntity> entityListDoor) {
        return entityListDoor.stream().map(entity -> {
            DoorDimensionsDTO dto = new DoorDimensionsDTO();
            dto.setDoorHeight(entity.getDoorHeight());
            dto.setDoorWidth(entity.getDoorWidth());
            dto.setDoorThickness(entity.getDoorThickness());

            return dto;
        }).collect(Collectors.toList());
    }
}