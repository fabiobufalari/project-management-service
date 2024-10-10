package com.bufalari.building.converts;

import com.bufalari.building.entity.BaseboardEntity;
import com.bufalari.building.requestDTO.BaseboardDTO;
import org.springframework.stereotype.Component;

@Component
public class BaseboardConverter {

    public BaseboardEntity toEntity(BaseboardDTO dto) {
        BaseboardEntity entity = new BaseboardEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setHeightInches(dto.getHeightInches());
        entity.setPaintColor(dto.getPaintColor());
        return entity;
    }
}
