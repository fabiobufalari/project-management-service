package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalMaterialsDTO {
    @Schema(description = "Total lumber calculation details")
    private LumberDTO lumber;
    @Schema(description = "Total drywall calculation details")
    private DrywallDTO drywall;
    @Schema(description = "Total concrete calculation details")
    private ConcreteDTO concrete;
    @Schema(description = "Total insulation calculation details")
    private InsulationDTO insulation;
    @Schema(description = "Total fasteners (nails and screws) calculation details")
    private FastenerDTO nailsAndScrews;
    // Adicionar outros totais de materiais aqui, se necessário
}