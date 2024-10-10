package com.bufalari.building.service;



import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomEntity createRoom(RoomEntity room) {
        return roomRepository.save(room);  // Save a new room
    }

    public RoomEntity addWallToRoom(RoomEntity room, WallEntity wall) {
        room.getWalls().add(wall);
        wall.setRoom(room);
        return roomRepository.save(room);  // Persist the updated room with the new wall
    }

    public Optional<RoomEntity> getRoomById(Long roomId) {
        return roomRepository.findById(roomId);
    }
}
