package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // <<<--- IMPORT UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalconyCalculationDTO {

    @Schema(description = "Unique identifier (UUID) of the balcony, if persisted as a separate entity")
    private UUID id; // <<<--- Adicionado ID UUID (se Balcony é uma entidade com ID próprio)

    private boolean hasBalcony;
    private double balconyAreaSquareFeet;
    private String railingMaterial;
    private String floorMaterial;
    private String structureType;
    private double estimatedCostPerSquareFoot;
    private double totalEstimatedCost;
    private String codeReference; // Referência de código para materiais/cálculo
}