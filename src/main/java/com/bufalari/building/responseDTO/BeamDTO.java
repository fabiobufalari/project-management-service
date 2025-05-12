package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeamDTO {
    @Schema(description = "Type or specification of the beam", example = "2x10 Treated Lumber")
    private String type;
    @Schema(description = "Quantity of this beam type required", example = "12")
    private int quantity;
    // Adicionar comprimento (length) se necessário
    // @Schema(description = "Length of each beam in feet", example = "16")
    // private double lengthFoot;
}