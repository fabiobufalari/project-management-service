package com.bufalari.building.converts;

import com.bufalari.building.entity.RoofEntity;
import com.bufalari.building.requestDTO.RoofDTO;
import org.springframework.stereotype.Component;

@Component
public class RoofConverter {

    public RoofEntity toEntity(RoofDTO dto) {
        RoofEntity entity = new RoofEntity();
        entity.setMaterial(dto.getMaterial());
        entity.setAreaSquareFeet(dto.getAreaSquareFeet());
        entity.setSlopeDegree(dto.getSlopeDegree());
        entity.setStructureType(dto.getStructureType());
        entity.setInsulationRValue(dto.getInsulationRValue());
        return entity;
    }
}
