package com.bufalari.building.converts;

import com.bufalari.building.entity.PaintingEntity;
import com.bufalari.building.requestDTO.PaintingDTO;
import org.springframework.stereotype.Component;

@Component
public class PaintingConverter {

    public PaintingEntity toEntity(PaintingDTO dto) {
        PaintingEntity entity = new PaintingEntity();
        entity.setInteriorWallColor(dto.getInteriorWallColor());
        entity.setExteriorWallColor(dto.getExteriorWallColor());
        return entity;
    }
}
