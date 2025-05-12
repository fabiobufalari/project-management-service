package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoofCalculationDTO {

    @Schema(description = "Unique identifier (UUID) of the roof, if persisted as a separate entity")
    private UUID id; // <<<--- Adicionado ID UUID (se Roof é uma entidade com ID próprio)

    private String material;
    private double areaSquareFeet;
    private int slopeDegree;
    private String structureType;
    private String insulationRValue;
    private List<BeamDTO> beams;
    private List<JoistDTO> joists;
    private String codeReference;
}