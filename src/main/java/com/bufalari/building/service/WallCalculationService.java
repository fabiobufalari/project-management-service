package com.bufalari.building.service;

import com.bufalari.building.entity.WallEntity;
import org.springframework.stereotype.Service;

@Service
public class WallCalculationService {

    public double calculateLinearFootage(WallEntity wall) {
        return wall.getLength();  // Linear footage is simply the length of the wall
    }

    public double calculateSquareFootage(WallEntity wall) {
        return wall.getLength() * wall.getHeight();  // Square footage is length * height
    }

    public WallEntity performWallCalculations(WallEntity wall) {
        double linearFootage = calculateLinearFootage(wall);
        double squareFootage = calculateSquareFootage(wall);

        wall.setLinearFootage(linearFootage);
        wall.setSquareFootage(squareFootage);

        return wall;  // Return the entity with updated values
    }
}
