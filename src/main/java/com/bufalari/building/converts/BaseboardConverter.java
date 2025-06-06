package com.bufalari.building.converts;

import com.bufalari.building.entity.BaseboardEntity;
import com.bufalari.building.requestDTO.BaseboardDTO;
import org.springframework.stereotype.Component;

@Component
public class BaseboardConverter {

    public BaseboardEntity toEntity(BaseboardDTO dto) {
        if (dto == null) return null;
        return BaseboardEntity.builder()
                .material(dto.getMaterial())
                .heightInches(dto.getHeightInches())
                .paintColor(dto.getPaintColor())
                .build();
    }

    public BaseboardDTO toDto(BaseboardEntity entity) {
        if (entity == null) return null;
        return new BaseboardDTO(
                entity.getMaterial(),
                entity.getHeightInches(),
                entity.getPaintColor()
        );
    }
}