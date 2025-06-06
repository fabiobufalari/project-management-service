package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoistDTO {
    @Schema(description = "Type or specification of the joist", example = "2x8 Floor Joist")
    private String type;
    @Schema(description = "Quantity of this joist type required", example = "45")
    private int quantity;
    // Adicionar comprimento (length) se necessário
    // @Schema(description = "Length of each joist in feet", example = "12")
    // private double lengthFoot;
}