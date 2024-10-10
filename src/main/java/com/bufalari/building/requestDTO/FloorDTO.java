package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// FloorDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorDTO {
    private String floorId;
    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;
    private boolean isWetArea; // Indica se o ambiente é área molhada

    private List<WallDTO> walls;
}