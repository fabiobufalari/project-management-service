package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.DoorDimensionsDTO;
import com.bufalari.building.entity.DoorDimensionsEntity; // Usa entidade do pacote 'model'
import org.slf4j.Logger; // Logger
import org.slf4j.LoggerFactory; // Logger
import org.springframework.stereotype.Component;

import java.util.Collections; // Para lista vazia
import java.util.List;
import java.util.stream.Collectors;

import static com.bufalari.building.util.UnitConversionUtils.convertStringToInches;


@Component
public class ConvertDoorDimensionsTo {

    private static final Logger log = LoggerFactory.getLogger(ConvertDoorDimensionsTo.class);

    public List<DoorDimensionsEntity> convertDoorDTOsToEntities(List<DoorDimensionsDTO> dtoListDoors) {
        if (dtoListDoors == null) {
            return Collections.emptyList(); // Retorna lista vazia se a entrada for nula
        }
        return dtoListDoors.stream()
                .map(dto -> {
                    if (dto == null) {
                        log.warn("Encontrado DoorDimensionsDTO nulo na lista durante conversão para entidade.");
                        return null; // Ou pular/lançar exceção
                    }
                    DoorDimensionsEntity entity = new DoorDimensionsEntity();
                    // ID da entidade DoorDimensionsEntity (Long) será gerado pelo banco
                    entity.setDoorHeightFoot(dto.getDoorHeightFoot());
                    // A conversão de String para inches deve ser robusta
                    try {
                        entity.setDoorHeightInches(convertStringToInches(dto.getDoorHeightInches()));
                        entity.setDoorWidthInches(convertStringToInches(dto.getDoorWidthInches()));
                    } catch (NumberFormatException e) {
                        log.error("Erro ao converter string de polegadas para double no DoorDTO: Altura='{}', Largura='{}'",
                                dto.getDoorHeightInches(), dto.getDoorWidthInches(), e);
                        // Definir como 0 ou lançar uma exceção específica de conversão
                        entity.setDoorHeightInches(0.0);
                        entity.setDoorWidthInches(0.0);
                    }
                    entity.setDoorWidthFoot(dto.getDoorWidthFoot());
                    entity.setDoorThickness(dto.getDoorThickness());
                    return entity;
                })
                .filter(java.util.Objects::nonNull) // Remove nulos se algum DTO for nulo
                .collect(Collectors.toList());
    }

    public List<DoorDimensionsDTO> convertDoorEntitiesToDto(List<DoorDimensionsEntity> entityListDoor) {
        if (entityListDoor == null) {
            return Collections.emptyList();
        }
        return entityListDoor.stream()
                .map(entity -> {
                    if (entity == null) {
                        log.warn("Encontrado DoorDimensionsEntity nulo na lista durante conversão para DTO.");
                        return null;
                    }
                    DoorDimensionsDTO dto = new DoorDimensionsDTO();
                    // ID não é mapeado de volta para o DTO neste caso (DTO não tem ID)
                    dto.setDoorHeightFoot(entity.getDoorHeightFoot());
                    // A conversão de double (inches) para String formatada (ex: "6 1/2") precisaria de lógica adicional
                    // Aqui, vamos assumir que o DTO pode aceitar double para polegadas, ou a conversão para String será simplificada
                    dto.setDoorHeightInches(entity.getDoorHeightInches() != null ? String.valueOf(entity.getDoorHeightInches()) : null);
                    dto.setDoorWidthFoot(entity.getDoorWidthFoot());
                    dto.setDoorWidthInches(entity.getDoorWidthInches() != null ? String.valueOf(entity.getDoorWidthInches()) : null);
                    dto.setDoorThickness(entity.getDoorThickness());
                    return dto;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}