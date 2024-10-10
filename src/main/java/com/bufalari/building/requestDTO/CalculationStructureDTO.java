package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationStructureDTO {
    private String floorId;
    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;
    private List<WallDTO> walls;
    private CeilingDTO ceiling;
    private BaseboardDTO baseboards;
    private PaintingDTO painting;
    private BalconyDTO balcony;
    private BathroomAccessoriesDTO bathroomAccessories;
    private KitchenAccessoriesDTO kitchenAccessories;
    private RoofDTO roof;
}