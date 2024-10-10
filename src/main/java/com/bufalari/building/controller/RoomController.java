package com.bufalari.building.controller;

import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomEntity> createRoom(@RequestBody RoomEntity room) {
        RoomEntity savedRoom = roomService.createRoom(room);
        return ResponseEntity.ok(savedRoom);
    }

    @PostMapping("/{roomId}/walls")
    public ResponseEntity<RoomEntity> addWallToRoom(@PathVariable Long roomId, @RequestBody WallEntity wall) {
        Optional<RoomEntity> roomOptional = roomService.getRoomById(roomId);
        if (roomOptional.isPresent()) {
            RoomEntity room = roomOptional.get();
            RoomEntity updatedRoom = roomService.addWallToRoom(room, wall);
            return ResponseEntity.ok(updatedRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
