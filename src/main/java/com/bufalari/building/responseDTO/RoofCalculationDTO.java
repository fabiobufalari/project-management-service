package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoofCalculationDTO {
    private String material;
    private double areaSquareFeet;
    private int slopeDegree;
    private String structureType;
    private String insulationRValue;
    private List<BeamDTO> beams;
    private List<JoistDTO> joists;
    private String codeReference;
}
