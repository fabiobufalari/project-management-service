package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallRoomMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wall_id")
    private WallEntity wall;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    private String side; // "Left", "Right", "External"
}