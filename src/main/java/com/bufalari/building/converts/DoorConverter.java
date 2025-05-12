package com.bufalari.building.converts;

import com.bufalari.building.entity.DoorEntity;
import com.bufalari.building.requestDTO.DimensionDTO;
import com.bufalari.building.requestDTO.DoorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DoorConverter {

    private static final Logger log = LoggerFactory.getLogger(DoorConverter.class);

    public DoorEntity toEntity(DoorDTO dto) {
        if (dto == null) {
            log.warn("DoorDTO is null, cannot convert to entity.");
            return null;
        }
        DoorEntity entity = new DoorEntity();
        // Se DoorDTO tiver um campo para o UUID da entidade (para update), mapeie aqui:
        // if (dto.getEntityUuid() != null) entity.setId(dto.getEntityUuid());
        entity.setDoorId(dto.getDoorId());

        DimensionDTO dimensions = dto.getDimensions();
        if (dimensions != null) {
            entity.setWidthFoot(dimensions.getWidthFoot());
            entity.setWidthInches(dimensions.getWidthInches());
            entity.setHeightFoot(dimensions.getHeightFoot());
            entity.setHeightInches(dimensions.getHeightInches());
            entity.setThicknessInch(dimensions.getThicknessInch());
            // Os campos width e height (double) serão calculados no @PrePersist/@PreUpdate da entidade
        } else {
            log.warn("DimensionsDTO is null for DoorDTO with doorId: {}. Dimensions will be default.", dto.getDoorId());
            entity.setWidthFoot(0); // Definir padrões ou validar
            entity.setHeightFoot(0);
        }
        return entity;
    }

    public DoorDTO toDto(DoorEntity entity) {
        if (entity == null) return null;
        DoorDTO dto = new DoorDTO();
        // Se DoorDTO tiver um campo para o UUID da entidade, mapeie aqui:
        // dto.setEntityUuid(entity.getId());
        dto.setDoorId(entity.getDoorId());

        DimensionDTO dims = new DimensionDTO(
                entity.getWidthFoot(),
                entity.getWidthInches(),
                entity.getHeightFoot(),
                entity.getHeightInches(),
                entity.getThicknessInch()
        );
        dto.setDimensions(dims);
        return dto;
    }
}