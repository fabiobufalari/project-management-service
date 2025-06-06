package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponseDTO {

    @Schema(description = "Calculations for external walls")
    private List<WallCalculationDTO> calculationExternal; // WallCalculationDTO usará UUID para ID da parede

    @Schema(description = "Calculations for internal walls")
    private List<WallCalculationDTO> calculationInternal; // WallCalculationDTO usará UUID para ID da parede

    @Schema(description = "Calculation details for the roof")
    private RoofCalculationDTO roof; // RoofCalculationDTO usará UUID para ID do telhado

    @Schema(description = "Calculation details for the balcony")
    private BalconyCalculationDTO balcony; // BalconyCalculationDTO usará UUID para ID da varanda

    @Schema(description = "Summary of total materials required")
    private TotalMaterialsDTO totalMaterials;
}