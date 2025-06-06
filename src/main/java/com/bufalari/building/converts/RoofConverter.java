package com.bufalari.building.converts;

import com.bufalari.building.entity.RoofEntity;
import com.bufalari.building.requestDTO.RoofDTO;
import org.springframework.stereotype.Component;

@Component
public class RoofConverter {

    public RoofEntity toEntity(RoofDTO dto) {
        if (dto == null) return null;
        return RoofEntity.builder()
                .material(dto.getMaterial())
                .areaSquareFeet(dto.getAreaSquareFeet())
                .slopeDegree(dto.getSlopeDegree())
                .structureType(dto.getStructureType())
                .insulationRValue(dto.getInsulationRValue())
                .build();
    }

    public RoofDTO toDto(RoofEntity entity) {
        if (entity == null) return null;
        return new RoofDTO(
                entity.getMaterial(),
                entity.getAreaSquareFeet(),
                entity.getSlopeDegree(),
                entity.getStructureType(),
                entity.getInsulationRValue()
        );
    }
}