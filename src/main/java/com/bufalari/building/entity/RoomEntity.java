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
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BYTEA")
    private UUID uuid;

    private String roomType; // Tipo de cômodo (ex: "Sala", "Quarto")
    private int floorNumber;
    private boolean isWetArea;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "wall_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "wall_id")
    )
    private List<WallEntity> walls = new ArrayList<>();
}