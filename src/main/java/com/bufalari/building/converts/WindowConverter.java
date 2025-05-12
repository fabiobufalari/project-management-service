package com.bufalari.building.converts;

import com.bufalari.building.entity.WindowEntity;
import com.bufalari.building.requestDTO.DimensionDTO;
import com.bufalari.building.requestDTO.WindowDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WindowConverter {

    private static final Logger log = LoggerFactory.getLogger(WindowConverter.class);

    public WindowEntity toEntity(WindowDTO dto) {
        if (dto == null) {
            log.warn("WindowDTO is null, cannot convert to entity.");
            return null;
        }
        WindowEntity entity = new WindowEntity();
        // if (dto.getEntityUuid() != null) entity.setId(dto.getEntityUuid());
        entity.setWindowId(dto.getWindowId());

        DimensionDTO dimensions = dto.getDimensions();
        if (dimensions != null) {
            entity.setWidthFoot(dimensions.getWidthFoot());
            entity.setWidthInches(dimensions.getWidthInches());
            entity.setHeightFoot(dimensions.getHeightFoot());
            entity.setHeightInches(dimensions.getHeightInches());
            entity.setThicknessInch(dimensions.getThicknessInch());
        } else {
            log.warn("DimensionsDTO is null for WindowDTO with windowId: {}. Dimensions will be default.", dto.getWindowId());
            entity.setWidthFoot(0);
            entity.setHeightFoot(0);
        }
        return entity;
    }

    public WindowDTO toDto(WindowEntity entity) {
        if (entity == null) return null;
        WindowDTO dto = new WindowDTO();
        // dto.setEntityUuid(entity.getId());
        dto.setWindowId(entity.getWindowId());

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