package com.bufalari.building.converts;

import com.bufalari.building.entity.CeilingEntity;
import com.bufalari.building.requestDTO.CeilingDTO; // Usaremos o mesmo DTO para request e response simples
import org.springframework.stereotype.Component;

@Component
public class CeilingConverter {

    public CeilingEntity toEntity(CeilingDTO dto) {
        if (dto == null) return null;
        return CeilingEntity.builder()
                .heightFoot(dto.getHeightFoot())
                .material(dto.getMaterial())
                .paintColor(dto.getPaintColor())
                .build();
    }

    public CeilingDTO toDto(CeilingEntity entity) {
        if (entity == null) return null;
        return new CeilingDTO( // Ou usar builder se o DTO tiver
                entity.getHeightFoot(),
                entity.getMaterial(),
                entity.getPaintColor()
        );
    }
}