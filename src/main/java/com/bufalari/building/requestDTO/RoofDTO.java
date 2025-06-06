package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoofDTO {
    private String material;
    private double areaSquareFeet;
    private int slopeDegree;
    private String structureType;
    private String insulationRValue;
}
