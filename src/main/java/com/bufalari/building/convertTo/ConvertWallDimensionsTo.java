package com.bufalari.building.convertTo;

import com.bufalari.building.DTO.WallDimensionsDTO;
import com.bufalari.building.enums.SideOfWall;
import com.bufalari.building.entity.WallMeasurementEntity; // Usa entidade do pacote 'model'
// import com.bufalari.building.util.convert; // Import específico de métodos não é necessário com import static
import lombok.RequiredArgsConstructor; // Usar Lombok
import org.slf4j.Logger; // Logger
import org.slf4j.LoggerFactory; // Logger
import org.springframework.stereotype.Component;

import static com.bufalari.building.util.UnitConversionUtils.convertInchesToFeet;
import static com.bufalari.building.util.UnitConversionUtils.convertStringToInches;


@Component
@RequiredArgsConstructor // Injeta dependências de conversor
public class ConvertWallDimensionsTo {

    private static final Logger log = LoggerFactory.getLogger(ConvertWallDimensionsTo.class);

    // Injetar os conversores de Door e Window se forem usados como beans @Component
    private final ConvertDoorDimensionsTo convertDoorDimensionsTo;
    private final ConvertWindowDimensionsTo convertWindowDimensionsTo;

    public WallMeasurementEntity convertToEntity(WallDimensionsDTO dto) {
        if (dto == null) {
            log.warn("WallDimensionsDTO nulo recebido para conversão para entidade.");
            return null;
        }
        WallMeasurementEntity entity = new WallMeasurementEntity();
        // ID da entidade WallMeasurementEntity (Long) será gerado pelo banco

        entity.setIdentifyWall(dto.getIdentifyWall());

        // Tratar conversões de String para double com cuidado
        try {
            entity.setWallLengthFoot( (dto.getWallLengthFoot() != null ? dto.getWallLengthFoot() : 0.0) +
                                    convertInchesToFeet(convertStringToInches(dto.getWallLengthInches())) );
            entity.setWallHeightFoot( (dto.getWallHeightFoot() != null ? dto.getWallHeightFoot() : 0.0) +
                                     convertInchesToFeet(convertStringToInches(dto.getWallHeightInches())) );
        } catch (NumberFormatException e) {
            log.error("Erro ao converter strings de dimensão da parede para números: Comprimento='{}', Altura='{}'",
                    dto.getWallLengthInches(), dto.getWallHeightInches(), e);
            // Definir valores padrão ou relançar exceção, dependendo da regra de negócio
            entity.setWallLengthFoot(dto.getWallLengthFoot() != null ? dto.getWallLengthFoot() : 0.0);
            entity.setWallHeightFoot(dto.getWallHeightFoot() != null ? dto.getWallHeightFoot() : 0.0);
        }

        entity.setWallThickness(dto.getWallThickness());

        // Converter String para Enum SideOfWall
        if (dto.getSideOfWall() != null) {
            try {
                entity.setSideOfWall(SideOfWall.valueOf(dto.getSideOfWall().toUpperCase()));
            } catch (IllegalArgumentException e) {
                log.warn("Valor inválido para SideOfWall: '{}'. Usando null ou valor padrão.", dto.getSideOfWall());
                entity.setSideOfWall(null); // Ou um valor padrão, ou lançar exceção
            }
        }

        entity.setStudSpacing(dto.getStudSpacing());

        // Usar os conversores injetados
        entity.setDoors(convertDoorDimensionsTo.convertDoorDTOsToEntities(dto.getDoor()));
        entity.setWindows(convertWindowDimensionsTo.convertWindowDTOsToEntities(dto.getWindow()));

        // Garantir que a relação bidirecional seja definida (se as entidades filhas tiverem referência à WallMeasurementEntity)
        if (entity.getDoors() != null) {
            entity.getDoors().forEach(door -> door.setWallMeasurement(entity));
        }
        if (entity.getWindows() != null) {
            entity.getWindows().forEach(window -> window.setWallMeasurement(entity));
        }

        return entity;
    }

    public WallDimensionsDTO convertToDTO(WallMeasurementEntity entity) {
        if (entity == null) {
            log.warn("WallMeasurementEntity nula recebida para conversão para DTO.");
            return null;
        }
        WallDimensionsDTO dto = new WallDimensionsDTO();

        dto.setIdentifyWall(entity.getIdentifyWall());

        // Para converter de double (pés) de volta para "X pé Y polegadas" é mais complexo.
        // Por simplicidade, o DTO manterá os valores em pés decimais ou precisará de lógica de formatação.
        dto.setWallLengthFoot(entity.getWallLengthFoot());
        // dto.setWallLengthInches(formatInchesFromFeet(entity.getWallLengthFoot())); // Exemplo de formatação
        dto.setWallHeightFoot(entity.getWallHeightFoot());
        // dto.setWallHeightInches(formatInchesFromFeet(entity.getWallHeightFoot())); // Exemplo

        dto.setWallThickness(entity.getWallThickness());
        dto.setSideOfWall(entity.getSideOfWall() != null ? entity.getSideOfWall().name() : null);
        dto.setStudSpacing(entity.getStudSpacing());

        // Usar os conversores injetados
        dto.setDoor(convertDoorDimensionsTo.convertDoorEntitiesToDto(entity.getDoors()));
        dto.setWindow(convertWindowDimensionsTo.convertWindowEntitiesToDto(entity.getWindows()));

        // Informações calculadas (area, numberOfStuds) geralmente não são parte do DTO de "dimensões",
        // mas sim de um DTO de "resultado de cálculo". Se precisar delas aqui, adicione os campos ao DTO.
        // dto.setCalculatedArea(entity.getArea());
        // dto.setCalculatedNumberOfStuds(entity.getNumberOfStuds());

        return dto;
    }

    // Exemplo de método auxiliar para formatar polegadas (não incluído no original)
    /*
    private String formatInchesFromFeet(Double totalFeet) {
        if (totalFeet == null) return null;
        double feetPart = Math.floor(totalFeet);
        double inchesPart = (totalFeet - feetPart) * 12.0;
        // Lógica para formatar inchesPart em string "X Y/Z" seria necessária aqui
        return String.format("%.2f", inchesPart); // Formatação simples
    }
    */
}