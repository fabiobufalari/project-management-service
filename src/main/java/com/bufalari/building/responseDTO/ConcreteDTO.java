package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcreteDTO {
    @Schema(description = "Total volume of concrete required in cubic yards", example = "25.5")
    private double totalCubicYards;
    @Schema(description = "List of areas where this concrete will be used (e.g., Foundation, Slabs)",
            example = "[\"Foundation Walls\", \"Ground Floor Slab\"]")
    private List<String> usedFor;
    @Schema(description = "Code reference for the type/mix of concrete", example = "CONC-3000PSI")
    private String codeReference;
}