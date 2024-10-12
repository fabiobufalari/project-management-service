package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BYTEA")
    private UUID uuid; // Atributo UUID

    @Column(columnDefinition = "BYTEA")
    private UUID roomUuid;

    private String wallId;

    private String description;
    private String type;
    private double lengthFoot;
    private double lengthInches;
    private double heightFoot;
    private double heightInches;
    private double wallThicknessInch;
    private String materialType;
    private double linearFootage;
    private double squareFootage;
    private boolean isExternal;
    private int floorNumber;
    @Column(name = "stud_count", nullable = false, columnDefinition = "integer default 0")
    private int studCount;

    @Column(name = "stud_linear_footage", nullable = false, columnDefinition = "double precision default 0.0")
    private double studLinearFootage;

    private double Length;
    private double height;
    private String roomSides;
    private Integer numberOfPlates;
    private Double studSpacingInch;

    public void setMaterial(String material) {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<WindowEntity> windows;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<DoorEntity> doors;

    @ManyToMany(mappedBy = "walls")
    private List<RoomEntity> rooms = new ArrayList<>();

    // Método auxiliar para calcular o comprimento total em pés
    public double getTotalLengthInFeet() {
        return lengthFoot + (lengthInches / 12);
    }

    // Método auxiliar para calcular a altura total em pés
    public double getTotalHeightInFeet() {
        return heightFoot + (heightInches / 12);
    }

}