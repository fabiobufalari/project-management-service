package com.bufalari.building.converts;

import com.bufalari.building.entity.BalconyEntity;
import com.bufalari.building.requestDTO.BalconyDTO;
import org.springframework.stereotype.Component;

@Component
public class BalconyConverter {

    public BalconyEntity toEntity(BalconyDTO dto) {
        if (dto == null) return null;
        return BalconyEntity.builder()
                .hasBalcony(dto.isHasBalcony())
                .balconyAreaSquareFeet(dto.getBalconyAreaSquareFeet())
                .railingMaterial(dto.getRailingMaterial())
                .floorMaterial(dto.getFloorMaterial())
                .structureType(dto.getStructureType())
                .estimatedCostPerSquareFoot(dto.getEstimatedCostPerSquareFoot())
                .totalEstimatedCost(dto.getTotalEstimatedCost())
                .build();
    }

    public BalconyDTO toDto(BalconyEntity entity) {
        if (entity == null) return null;
        return new BalconyDTO(
                entity.isHasBalcony(),
                entity.getBalconyAreaSquareFeet(),
                entity.getRailingMaterial(),
                entity.getFloorMaterial(),
                entity.getStructureType(),
                entity.getEstimatedCostPerSquareFoot(),
                entity.getTotalEstimatedCost()
        );
    }
}