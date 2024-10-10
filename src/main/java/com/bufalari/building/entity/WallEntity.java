package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// WallEntity
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallId;

    private String description;
    private String type;
    private double lengthFoot;
    private double lengthInches;
    private double heightFoot;
    private double heightInches;
    private double wallThicknessInch;
    private String material;
    private boolean isExternal; // Define se a parede é externa ou interna

    private double length;    // Length of the wall
    private double height;    // Height of the wall
    private double linearFootage;  // Calculated value
    private double squareFootage;  // Calculated value

    // Adiciona listas de janelas e portas
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<WindowEntity> windows;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<DoorEntity> doors;

    // Referência para FloorEntity
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private FloorEntity floor;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;
}