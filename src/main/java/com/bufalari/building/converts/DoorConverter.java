// DoorConverter.java
package com.bufalari.building.converts;

import com.bufalari.building.entity.DoorEntity;
import com.bufalari.building.requestDTO.DoorDTO;
import org.springframework.stereotype.Component;

@Component
public class DoorConverter {

    public DoorEntity toEntity(DoorDTO dto) {
        DoorEntity entity = new DoorEntity();
        entity.setDoorId(dto.getDoorId());
        entity.setWidth(dto.getDimensions().getWidthFoot() + (dto.getDimensions().getWidthInches() / 12));
        entity.setHeight(dto.getDimensions().getHeightFoot() + (dto.getDimensions().getHeightInches() / 12));
        entity.setThicknessInch(dto.getDimensions().getThicknessInch());
        return entity;
    }
}