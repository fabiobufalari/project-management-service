// WindowConverter.java
package com.bufalari.building.converts;

import com.bufalari.building.entity.WindowEntity;
import com.bufalari.building.requestDTO.WindowDTO;
import org.springframework.stereotype.Component;

@Component
public class WindowConverter {

    public WindowEntity toEntity(WindowDTO dto) {
        WindowEntity entity = new WindowEntity();
        entity.setWindowId(dto.getWindowId());
        entity.setWidth(dto.getDimensions().getWidthFoot() + (dto.getDimensions().getWidthInches() / 12));
        entity.setHeight(dto.getDimensions().getHeightFoot() + (dto.getDimensions().getHeightInches() / 12));
        entity.setThicknessInch(dto.getDimensions().getThicknessInch());
        return entity;
    }
}