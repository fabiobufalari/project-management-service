package com.bufalari.building.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bathroom_accessories")
public class BathroomAccessoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "Shower", "Sink", "Toilet"
    private String material;
    private double height; // Usar se necessário (ex: altura do chuveiro)

    // Getters e Setters
}