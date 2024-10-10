package com.bufalari.building.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalconyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean hasBalcony;
    private double balconyAreaSquareFeet;
    private String railingMaterial;
    private String floorMaterial;
    private String structureType;
    private double estimatedCostPerSquareFoot;
    private double totalEstimatedCost;
}
