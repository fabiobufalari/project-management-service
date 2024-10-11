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

    public double Length;

    public double height;
    public String roomSides;


    public void setMaterial(String material) {
    }
}