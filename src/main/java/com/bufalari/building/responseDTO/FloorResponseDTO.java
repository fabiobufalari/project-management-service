package com.bufalari.building.responseDTO;

import com.bufalari.building.requestDTO.CeilingDTO;
import com.bufalari.building.requestDTO.WallDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorResponseDTO {

    @Schema(description = "Unique identifier (UUID) of the floor")
    private UUID floorUuid;

    @Schema(description = "Legacy Long ID of the floor (if applicable)", nullable = true)
    private Long floorIdLegacy;

    @Schema(description = "Floor number")
    private int floorNumber;

    @Schema(description = "Total area of the floor in square feet")
    private double areaSquareFeet;

    @Schema(description = "Indicates if the floor is heated")
    private boolean heated;

    @Schema(description = "Primary material of the floor structure/finish", nullable = true)
    private String material;

    @Schema(description = "List of walls in this floor")
    private List<WallDTO> walls; // WallDTO para resposta (pode ser diferente do WallDTO de request)

    // Incluir DTOs de resposta para outros componentes do andar (Ceiling, Baseboard, etc.)
    @Schema(description = "Ceiling details", nullable = true)
    private CeilingDTO ceiling;
    // ... outros componentes
}