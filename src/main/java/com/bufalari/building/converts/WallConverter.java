package com.bufalari.building.converts;

import com.bufalari.building.entity.DoorEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.entity.WindowEntity;
import com.bufalari.building.requestDTO.DoorDTO;
import com.bufalari.building.requestDTO.WallDTO;
import com.bufalari.building.requestDTO.WindowDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WallConverter {

    public WallEntity toEntity(WallDTO dto) {
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

        if (dto.getWindows() != null) {
            List<WindowEntity> windows = dto.getWindows().stream()
                .map(this::convertWindow)
                .collect(Collectors.toList());
            entity.setWindows(windows);
        }

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
}
