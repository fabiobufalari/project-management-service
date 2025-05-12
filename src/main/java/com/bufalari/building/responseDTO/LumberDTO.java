package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LumberDTO {
    @Schema(description = "Total lumber required in board feet", example = "1500.0")
    private double totalBoardFeet;
    @Schema(description = "List of available or recommended lumber types/sizes", example = "[\"2x4x8 SPF\", \"2x6x12 SPF\"]")
    private List<String> availableTypes; // Ou uma lista de DTOs mais detalhados de LumberItemDTO
    @Schema(description = "General code reference for lumber", example = "LUMBER-SPF-GEN")
    private String codeReference;
}