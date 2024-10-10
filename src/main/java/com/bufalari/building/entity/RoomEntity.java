package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomType;  // Type of room (e.g., "Living Room", "Bedroom")
    private int floorNumber;  // Floor where this room is located

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<WallEntity> walls = new ArrayList<>();  // Walls associated with this room

    // Constructor
    public RoomEntity() {}

    public RoomEntity(String roomType, int floorNumber) {
        this.roomType = roomType;
        this.floorNumber = floorNumber;
    }

}
