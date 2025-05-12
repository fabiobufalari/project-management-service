package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsulationDTO {
    @Schema(description = "Total area of insulation required in square feet", example = "1200.0")
    private double totalSquareFeet;
    @Schema(description = "R-Value of the insulation material", example = "R-19")
    private String rValue; // Pode ser String para incluir "R-"
    @Schema(description = "Code reference for the insulation type", example = "INSUL-BATT-R19")
    private String codeReference;
}