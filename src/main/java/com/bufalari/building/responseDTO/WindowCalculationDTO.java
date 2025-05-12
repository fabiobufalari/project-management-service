package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // <<<--- IMPORT UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowCalculationDTO {

    @Schema(description = "Unique identifier (UUID) of the Window entity this calculation refers to")
    private UUID id; // <<<--- Adicionado ID UUID da entidade WindowEntity

    @Schema(description = "Textual identifier of the window (e.g., W1, W2)", example = "W1")
    private String windowId;

    @Schema(description = "Width of the window in feet", example = "4.0")
    private double windowWidthFoot;
    // private int windowWidthInches; // Opcional, se preferir manter separado

    @Schema(description = "Height of the window in feet", example = "3.5")
    private double windowHeightFoot;
    // private int windowHeightInches; // Opcional

    @Schema(description = "Thickness of the window frame/unit in inches", example = "3.25")
    private double windowThicknessInch;

    @Schema(description = "Code reference for the window type/material", example = "WIN-VINYL-DBLHUNG-01")
    private String codeReference;
}