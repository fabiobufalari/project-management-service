package com.bufalari.building.converts;

import com.bufalari.building.entity.PaintingEntity;
import com.bufalari.building.requestDTO.PaintingDTO;
import org.springframework.stereotype.Component;

@Component
public class PaintingConverter {

    public PaintingEntity toEntity(PaintingDTO dto) {
        if (dto == null) return null;
        return PaintingEntity.builder()
                .interiorWallColor(dto.getInteriorWallColor())
                .exteriorWallColor(dto.getExteriorWallColor())
                .build();
    }

    public PaintingDTO toDto(PaintingEntity entity) {
        if (entity == null) return null;
        return new PaintingDTO(
                entity.getInteriorWallColor(),
                entity.getExteriorWallColor()
        );
    }
}