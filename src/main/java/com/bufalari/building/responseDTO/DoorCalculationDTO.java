package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // <<<--- IMPORT UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoorCalculationDTO {

    @Schema(description = "Unique identifier (UUID) of the Door entity this calculation refers to")
    private UUID id; // <<<--- Adicionado ID UUID da entidade DoorEntity

    @Schema(description = "Textual identifier of the door (e.g., D1, D2)", example = "D1")
    private String doorId;

    @Schema(description = "Width of the door in feet (e.g., 2.5 for 2'6\")", example = "2.5")
    private double doorWidthFoot; // Pode ser o total em pés decimais
    // private int doorWidthInches; // Ou manter separado se preferir

    @Schema(description = "Height of the door in feet (e.g., 6.67 for 6'8\")", example = "6.67")
    private double doorHeightFoot; // Pode ser o total em pés decimais
    // private int doorHeightInches; // Ou manter separado

    @Schema(description = "Thickness of the door in inches", example = "1.75")
    private double doorThicknessInch;

    @Schema(description = "Code reference for the door type/material", example = "DOOR-INT-WOOD-01")
    private String codeReference;
}