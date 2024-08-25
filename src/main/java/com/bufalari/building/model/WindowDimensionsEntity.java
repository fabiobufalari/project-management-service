package com.bufalari.building.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "window_dimensions")
public class WindowDimensionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double windowsWidth;
    private Double windowsHeight;
    private Double windowsThickness;

    @ManyToOne
    @JoinColumn(name = "wall_measurement_id")
    private WallMeasurementEntity wallMeasurement;
}
