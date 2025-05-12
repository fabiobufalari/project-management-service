package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudDTO {
    @Schema(description = "Type or dimension of the stud", example = "2x4x8'")
    private String type; // Ex: "2x4x8'", "2x6x10'"
    @Schema(description = "Quantity of this stud type required", example = "120")
    private int quantity;
}