package com.bufalari.building.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class WallId implements Serializable {
    private Long id;
    private UUID uuid;

    // Getters e Setters
}