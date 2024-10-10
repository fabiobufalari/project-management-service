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
public class WallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wallId;
    private String description;
    private String type;  // external or internal
    private double lengthFoot;
    private int lengthInches;
    private double heightFoot;
    private int heightInches;
    private double wallThicknessInch;
    private String material;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<WindowEntity> windows;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private List<DoorEntity> doors;
}
