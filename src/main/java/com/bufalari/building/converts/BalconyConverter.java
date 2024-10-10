package com.bufalari.building.converts;

import com.bufalari.building.entity.BalconyEntity;
import com.bufalari.building.requestDTO.BalconyDTO;
import org.springframework.stereotype.Component;

@Component
public class BalconyConverter {

    public BalconyEntity toEntity(BalconyDTO dto) {
        BalconyEntity entity = new BalconyEntity();
        entity.setHasBalcony(dto.isHasBalcony());
        entity.setBalconyAreaSquareFeet(dto.getBalconyAreaSquareFeet());
        entity.setRailingMaterial(dto.getRailingMaterial());
        entity.setFloorMaterial(dto.getFloorMaterial());
        entity.setStructureType(dto.getStructureType());
        entity.setEstimatedCostPerSquareFoot(dto.getEstimatedCostPerSquareFoot());
        entity.setTotalEstimatedCost(dto.getTotalEstimatedCost());
        return entity;
    }
}
