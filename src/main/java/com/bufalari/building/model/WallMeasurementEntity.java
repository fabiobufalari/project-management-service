package com.bufalari.building.model;

import com.bufalari.building.enums.SideOfWall;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class WallMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double area; // Total area in square feet.

    private String identifyWall;

    private Double wallLength;

    private Double wallHeight;

    private Double wallThickness; // Wall thickness

    private Double studSpacing;//distance between studs and other.

    @Enumerated(EnumType.STRING)
    private SideOfWall sideOfWall;// infortation side of wall.  1 = internal or 0(zero) = external


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "wallMeasurement")
    private List<WindowDimensionsEntity> windows = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "wallMeasurement")
    private List<DoorDimensionsEntity> doors = new ArrayList<>();


    public void setAreaToCalculate(double usableWallArea) {
    }

    public void setNumberOfStuds(int numberOfStuds) {
    }
}