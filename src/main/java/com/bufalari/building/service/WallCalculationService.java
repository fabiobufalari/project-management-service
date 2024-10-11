package com.bufalari.building.service;


import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.requestDTO.RoomSideDTO;
import org.springframework.stereotype.Service;

@Service
public class WallCalculationService {

    public double calculateLinearFootage(WallEntity wall) {
        return wall.getLength();
    }

    public double calculateSquareFootage(WallEntity wall) {
        return wall.getLength() * wall.getHeight();
    }

    public String determineMaterialType(WallEntity wall, RoomEntity room) {
        if (wall.isExternal()) {
            return "Concrete";
        } else if (room.isWetArea()) { // Agora room não será mais nulo
            return "Moisture Resistant Drywall";
        } else {
            return "Drywall";
        }
    }

    public WallEntity performWallCalculations(WallEntity wall) {

        return wall;
    }

    public double calculateLinearFootage(double lengthInFeet) {
        return lengthInFeet;
    }

    public double calculateSquareFootage(double lengthInFeet, double heightInFeet) {
        return lengthInFeet * heightInFeet;
    }

    public WallEntity calculateWall(WallEntity wallEntity) {
        // Calcula as medidas da parede
        wallEntity.setLinearFootage(calculateLinearFootage(wallEntity.getTotalLengthInFeet()));
        wallEntity.setSquareFootage(calculateSquareFootage(wallEntity.getTotalLengthInFeet(), wallEntity.getTotalHeightInFeet()));
        return wallEntity;
    }
}