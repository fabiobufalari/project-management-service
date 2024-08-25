package com.bufalari.building.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class DoorDimensionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double doorWidth;
    private Double doorHeight;
    private Double doorThickness;

    @ManyToOne
    @JoinColumn(name = "wall_id")
    private WallMeasurementEntity wallMeasurement;
}