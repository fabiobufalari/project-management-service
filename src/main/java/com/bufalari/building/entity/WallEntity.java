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
public class WallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long wallId;
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

    public void setMaterial(String material) {
    }
}