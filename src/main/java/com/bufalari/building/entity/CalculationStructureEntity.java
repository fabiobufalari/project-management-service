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
public class CalculationStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String floorId;
    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculation_structure_id")
    private List<WallEntity> walls;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ceiling_id", referencedColumnName = "id")
    private CeilingEntity ceiling;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baseboard_id", referencedColumnName = "id")
    private BaseboardEntity baseboards;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "painting_id", referencedColumnName = "id")
    private PaintingEntity painting;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balcony_id", referencedColumnName = "id")
    private BalconyEntity balcony;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bathroom_accessories_id", referencedColumnName = "id")
    private BathroomAccessoriesEntity bathroomAccessories;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchen_accessories_id", referencedColumnName = "id")
    private KitchenAccessoriesEntity kitchenAccessories;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roof_id", referencedColumnName = "id")
    private RoofEntity roof;
}
