package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.WindowDimensionsDTO;
import com.bufalari.building.model.WindowDimensionsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ConvertWindowDimensionsTo {
    public List<WindowDimensionsEntity> convertWindowDTOsToEntities(List<WindowDimensionsDTO> dtoListWindows) {
        return dtoListWindows.stream().map(dto -> {
            WindowDimensionsEntity entity = new WindowDimensionsEntity();
            entity.setWindowsHeight(dto.getWindowsHeight());
            entity.setWindowsWidth(dto.getWindowsWidth());
            entity.setWindowsThickness(dto.getWindowsThickness());

            return entity;
        }).collect(Collectors.toList());
    }

    public List<WindowDimensionsDTO> convertWindowEntitiesToDto(List<WindowDimensionsEntity> entityListWindows) {
        return entityListWindows.stream().map(entity -> {
            WindowDimensionsDTO dto = new WindowDimensionsDTO();
            dto.setWindowsHeight(entity.getWindowsHeight());
            dto.setWindowsWidth(entity.getWindowsWidth());
            dto.setWindowsThickness(entity.getWindowsThickness());

            return dto;
        }).collect(Collectors.toList());
    }
}
