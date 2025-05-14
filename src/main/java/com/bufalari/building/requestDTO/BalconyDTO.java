package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalconyDTO {
    private boolean hasBalcony;
    private double balconyAreaSquareFeet;
    private String railingMaterial;
    private String floorMaterial;
    private String structureType;
    private BigDecimal estimatedCostPerSquareFoot;
    private BigDecimal totalEstimatedCost;
}
