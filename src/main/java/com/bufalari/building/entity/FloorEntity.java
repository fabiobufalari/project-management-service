package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String floorId;
    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "floor_id")
    private List<WallEntity> walls;
}
