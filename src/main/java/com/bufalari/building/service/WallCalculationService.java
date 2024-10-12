package com.bufalari.building.service;


import com.bufalari.building.entity.RoomEntity;
import com.bufalari.building.entity.WallEntity;
import com.bufalari.building.requestDTO.RoomSideDTO;
import com.bufalari.building.responseDTO.StudCalculationResultDTO;
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
            return "Concrete"; // Parede externa sempre de concreto
        } else {
            // Se roomEntity não for encontrado, use um material padrão
            if (room == null) {
                return "Default Material";
            } else if (room.isWetArea()) {
                return "Moisture Resistant Drywall";
            } else {
                return "Drywall";
            }
        }
    }

    // ===>>> NOVO MÉTODO PARA CÁLCULO DE STUDS: <<<===
    public StudCalculationResultDTO calculateStuds(WallEntity wall) {
        // 1. Obter o espaçamento dos studs
        double studSpacing = wall.getStudSpacingInch();
        // 2. Calcular a quantidade de studs (inalterado)
        double totalLengthInInches = wall.getTotalLengthInFeet() * 12;
        int studCount = (int) Math.ceil(totalLengthInInches / studSpacing) + 1;

        // ===>>>  AJUSTE NO CÁLCULO DA METRAGEM: <<<===
        // 3. Calcular a altura dos studs (em pés) subtraindo a altura das plates
        double studHeightFeet = wall.getTotalHeightInFeet() - (wall.getNumberOfPlates() * 1.5 / 12);

        // Calcular a medida linear total dos studs (em pés)
        double studLinearFootage = studCount * studHeightFeet;

        // 4. Criar DTO de resposta
        StudCalculationResultDTO result = new StudCalculationResultDTO();
        result.setWallId(wall.getWallId());
        result.setStudCount(studCount);
        result.setStudLinearFootage(studLinearFootage);

        return result;
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