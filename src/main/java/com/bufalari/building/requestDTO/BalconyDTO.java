package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalconyDTO {
    private boolean hasBalcony;
    private double balconyAreaSquareFeet;
    private String railingMaterial;
    private String floorMaterial;
    private String structureType;
    private double estimatedCostPerSquareFoot;
    private double totalEstimatedCost;
}
