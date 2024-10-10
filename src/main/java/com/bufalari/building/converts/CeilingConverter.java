package com.bufalari.building.converts;

import com.bufalari.building.entity.CeilingEntity;
import com.bufalari.building.requestDTO.CeilingDTO;
import org.springframework.stereotype.Component;

@Component
public class CeilingConverter {

    public CeilingEntity toEntity(CeilingDTO dto) {
        CeilingEntity entity = new CeilingEntity();
        entity.setHeightFoot(dto.getHeightFoot());
        entity.setMaterial(dto.getMaterial());
        entity.setPaintColor(dto.getPaintColor());
        return entity;
    }
}
