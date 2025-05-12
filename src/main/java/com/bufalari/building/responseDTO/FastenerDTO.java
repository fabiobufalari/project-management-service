package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastenerDTO {
    @Schema(description = "Total weight of nails required in pounds", example = "25.0")
    private double nailsPounds;
    @Schema(description = "Total weight (or count) of screws required in pounds (or units)", example = "10.5")
    private double screwsPounds; // Ou int screwsCount;
    @Schema(description = "Code reference for fastener types", example = "FAST-MIX-01")
    private String codeReference;
}