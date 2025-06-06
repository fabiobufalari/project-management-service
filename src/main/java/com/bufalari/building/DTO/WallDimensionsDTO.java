package com.bufalari.building.DTO;

import lombok.Data;
import java.util.List;

@Data
public class WallDimensionsDTO {

    private String identifyWall; // Identificador da parede, como A1, A2, etc.
    private Double wallLengthFoot;
    private String wallLengthInches;
    private Double wallHeightFoot;
    private String wallHeightInches;
    private Double wallThickness;
    private String sideOfWall; // 1 = internal or 0(zero) = external
    private Double studSpacing;//distance between studs and other.

    //private TopPlateDTO topPlateDTO;

    private List<WindowDimensionsDTO> window; // Lista de janelas associadas
    private List<DoorDimensionsDTO> door; // Lista de portas associadas


}