package com.bufalari.building.service;

import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
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
            return "Concrete"; // Paredes externas usam concreto
        } else if (room.isWetArea()) {
            return "Moisture Resistant Drywall"; // Paredes internas de áreas molhadas
        } else {
            return "Drywall"; // Paredes internas de áreas secas
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

        // Lógica para determinar o tipo de material da parede (implementar de acordo com as suas regras)
        // Neste exemplo, assumimos que todas as paredes externas são de concreto e as internas de drywall
        String materialType = determineMaterialType(wallEntity, null); // Passe o RoomEntity correto aqui, se necessário
        wallEntity.setMaterialType(materialType);

        return wallEntity;
    }
}