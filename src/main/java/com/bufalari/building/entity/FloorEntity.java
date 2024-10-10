package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long floorId;
    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;
    private boolean isWetArea;

    // Relacionamento com ProjectEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "wall_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "wall_id")
    )
    private List<WallEntity> walls = new ArrayList<>();

    // ... (Outros atributos e relacionamentos da FloorEntity) ...
}