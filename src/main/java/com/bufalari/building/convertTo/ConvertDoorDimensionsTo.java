package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.DoorDimensionsDTO;
import com.bufalari.building.model.DoorDimensionsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.bufalari.building.util.convert.convertStringToInches;

@Component
public class ConvertDoorDimensionsTo {

    public List<DoorDimensionsEntity> convertDoorDTOsToEntities(List<DoorDimensionsDTO> dtoListDoors) {
        return dtoListDoors.stream().map(dto -> {
            DoorDimensionsEntity entity = new DoorDimensionsEntity();
            entity.setDoorHeightFoot(dto.getDoorHeightFoot());
            entity.setDoorHeightInches(convertStringToInches(dto.getDoorHeightInches()));
            entity.setDoorWidthFoot(dto.getDoorWidthFoot());
            entity.setDoorWidthInches(convertStringToInches(dto.getDoorWidthInches()));
            entity.setDoorThickness(dto.getDoorThickness());


            return entity;
        }).collect(Collectors.toList());
    }

    public List<DoorDimensionsDTO> convertDoorEntitiesToDto(List<DoorDimensionsEntity> entityListDoor) {
        return entityListDoor.stream().map(entity -> {
            DoorDimensionsDTO dto = new DoorDimensionsDTO();
            dto.setDoorHeightFoot(entity.getDoorHeightFoot());
            //dto.setDoorHeightInches(convertStringToInches(entity.getDoorHeightInches()));
            dto.setDoorWidthFoot(entity.getDoorWidthFoot());
            //dto.setDoorWidthInches(entity.getDoorWidthInches());
            dto.setDoorThickness(entity.getDoorThickness());

            return dto;
        }).collect(Collectors.toList());
    }
}