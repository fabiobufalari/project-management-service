package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrywallDTO {
    @Schema(description = "Total number of drywall sheets required", example = "150")
    private int totalSheets;
    @Schema(description = "Size of the drywall sheets (e.g., 4x8 ft, 4x12 ft)", example = "4x8 ft")
    private String sheetSize;
    @Schema(description = "Code reference for the drywall type", example = "DRYWALL-STD-1/2")
    private String codeReference;
}