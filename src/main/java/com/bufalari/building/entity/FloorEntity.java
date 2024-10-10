package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// FloorEntity
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId;

    private int floorNumber;
    private double areaSquareFeet;
    private boolean heated;
    private String material;
    private boolean isWetArea; // Define se o ambiente é área molhada


    // Relacionamento para o teto (ceiling)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ceiling_id", referencedColumnName = "id")
    private CeilingEntity ceiling;

    // Relacionamento para os rodapés (baseboards)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baseboard_id", referencedColumnName = "id")
    private BaseboardEntity baseboards;

    // Relacionamento para a pintura (painting)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "painting_id", referencedColumnName = "id")
    private PaintingEntity painting;

    // Relacionamento para a varanda (balcony)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balcony_id", referencedColumnName = "id")
    private BalconyEntity balcony;

    // Relacionamento para acessórios do banheiro (bathroomAccessories)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bathroom_accessories_id", referencedColumnName = "id")
    private BathroomAccessoriesEntity bathroomAccessories;

    // Relacionamento para acessórios da cozinha (kitchenAccessories)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kitchen_accessories_id", referencedColumnName = "id")
    private KitchenAccessoriesEntity kitchenAccessories;

    // Relacionamento para o telhado (roof)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roof_id", referencedColumnName = "id")
    private RoofEntity roof;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<WallEntity> walls;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

}