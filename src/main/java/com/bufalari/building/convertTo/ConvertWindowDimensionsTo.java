package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.WindowDimensionsDTO;
import com.bufalari.building.entity.WindowDimensionsEntity; // Usa entidade do pacote 'model'
import org.slf4j.Logger; // Logger
import org.slf4j.LoggerFactory; // Logger
import org.springframework.stereotype.Component;

import java.util.Collections; // Para lista vazia
import java.util.List;
import java.util.stream.Collectors;

// Importar o utilitário de conversão se for usado (ex: para converter string "X Y/Z" para double)
// import static com.bufalari.building.util.convert.*;

@Component
public class ConvertWindowDimensionsTo {

    private static final Logger log = LoggerFactory.getLogger(ConvertWindowDimensionsTo.class);

    public List<WindowDimensionsEntity> convertWindowDTOsToEntities(List<WindowDimensionsDTO> dtoListWindows) {
        if (dtoListWindows == null) {
            return Collections.emptyList();
        }
        return dtoListWindows.stream()
                .map(dto -> {
                    if (dto == null) {
                        log.warn("Encontrado WindowDimensionsDTO nulo na lista durante conversão para entidade.");
                        return null;
                    }
                    WindowDimensionsEntity entity = new WindowDimensionsEntity();
                    // ID da entidade WindowDimensionsEntity (Long) será gerado pelo banco
                    entity.setWindowsHeightFoot(dto.getWindowsHeightFoot());
                    // Se DTO tivesse polegadas como String (ex: "6 1/2"), precisaria de conversão
                    // entity.setWindowsHeightInches(convertStringToInches(dto.getWindowsHeightInches()));
                    entity.setWindowsHeightInches(dto.getWindowsHeightInches()); // Assumindo que já é double

                    entity.setWindowsWidthFoot(dto.getWindowsWidthFoot());
                    // entity.setWindowsWidthInches(convertStringToInches(dto.getWindowsWidthInches()));
                    entity.setWindowsWidthInches(dto.getWindowsWidthInches()); // Assumindo que já é double

                    entity.setWindowsThickness(dto.getWindowsThickness());
                    return entity;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<WindowDimensionsDTO> convertWindowEntitiesToDto(List<WindowDimensionsEntity> entityListWindows) {
        if (entityListWindows == null) {
            return Collections.emptyList();
        }
        return entityListWindows.stream()
                .map(entity -> {
                    if (entity == null) {
                        log.warn("Encontrado WindowDimensionsEntity nulo na lista durante conversão para DTO.");
                        return null;
                    }
                    WindowDimensionsDTO dto = new WindowDimensionsDTO();
                    // ID não mapeado para DTO
                    dto.setWindowsHeightFoot(entity.getWindowsHeightFoot());
                    // Converter double (inches) para String formatada (ex: "X Y/Z") se necessário
                    dto.setWindowsHeightInches(entity.getWindowsHeightInches()); // Mantém como double

                    dto.setWindowsWidthFoot(entity.getWindowsWidthFoot());
                    dto.setWindowsWidthInches(entity.getWindowsWidthInches()); // Mantém como double

                    dto.setWindowsThickness(entity.getWindowsThickness());
                    return dto;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}