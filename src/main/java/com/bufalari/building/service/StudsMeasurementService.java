package com.bufalari.building.service;

import com.bufalari.building.enums.SideOfWall;
import com.bufalari.building.model.WallMeasurementEntity;
import com.bufalari.building.repository.WallMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.bufalari.building.util.convert.convertInchesToFeet;

@Service
public class StudsMeasurementService {
//    TopPlateCalculationService topPlateCalculationService = new TopPlateCalculationService();


    @Autowired
    private WallMeasurementRepository repository;

    @Transactional
    public WallMeasurementEntity calculateAndSaveMeasurement(WallMeasurementEntity entity) {
        // Validate input
        validateWallMeasurements(entity);

        // Ensure consistent units
        ensureConsistentUnits(entity);

        // Calculate wall area
        double area = calculateWallArea(entity);
        entity.setArea(area);

//        // Calculate top plate dimensions
//        Map<String, Integer> topPlateQuantities = topPlateCalculationService.calculateTopPlates(
//                entity.getWallLengthFoot(), entity.getWallLengthInches(),
//                entity.getWallHeightFoot(), entity.getWallHeightInches(),
//                2.0, 0.75 // Assuming standard top plate width and thickness in feet
//        );
//
//        // Print or log the top plate quantities
//        topPlateQuantities.forEach((size, quantity) ->
//                System.out.println("Size: " + size + ", Quantity: " + quantity)
//        );

        // Calculate for internal or external walls
        if (isInternalWall(entity)) {
            calculateInternalWallArea(entity);
        } else {
            calculateExternalWallArea(entity);
        }

        // Ensure relationships are set
        entity.getDoors().forEach(door -> door.setWallMeasurement(entity));
        entity.getWindows().forEach(window -> window.setWallMeasurement(entity));

        return repository.save(entity);
    }

    private boolean isInternalWall(WallMeasurementEntity entity) {
        return entity.getSideOfWall() == SideOfWall.INTERNAL;
    }

    private void validateWallMeasurements(WallMeasurementEntity entity) {
        if (entity.getWallLengthFoot() == null || entity.getWallLengthFoot() <= 0 ||
                entity.getWallHeightFoot() == null || entity.getWallHeightFoot() <= 0 ||
                entity.getWallThickness() <= 0) {
            throw new IllegalArgumentException("Wall dimensions must be greater than zero.");
        }
    }

    private void ensureConsistentUnits(WallMeasurementEntity entity) {
        if (entity.getWallLengthInches() == null) {
            entity.setWallLengthInches(0.0);
        }
        if (entity.getWallHeightInches() == null) {
            entity.setWallHeightInches(0.0);
        }

        entity.setWallLengthFoot(entity.getWallLengthFoot() + convertInchesToFeet(entity.getWallLengthInches()));
        entity.setWallHeightFoot(entity.getWallHeightFoot() + convertInchesToFeet(entity.getWallHeightInches()));

        entity.getWindows().forEach(window -> {
            if (window.getWindowsWidthInches() == null) {
                window.setWindowsWidthInches(0.0);
            }
            if (window.getWindowsHeightInches() == null) {
                window.setWindowsHeightInches(0.0);
            }

            window.setWindowsWidthFoot(window.getWindowsWidthFoot() + convertInchesToFeet(window.getWindowsWidthInches()));
            window.setWindowsHeightFoot(window.getWindowsHeightFoot() + convertInchesToFeet(window.getWindowsHeightInches()));
        });

        entity.getDoors().forEach(door -> {
            if (door.getDoorWidthInches() == null) {
                door.setDoorWidthInches(0.0);
            }
            if (door.getDoorHeightInches() == null) {
                door.setDoorHeightInches(0.0);
            }

            door.setDoorWidthFoot(door.getDoorWidthFoot() + convertInchesToFeet(door.getDoorWidthInches()));
            door.setDoorHeightFoot(door.getDoorHeightFoot() + convertInchesToFeet(door.getDoorHeightInches()));
        });
    }

    public double calculateWallArea(WallMeasurementEntity entity) {
        return entity.getWallLengthFoot() * entity.getWallHeightFoot();
    }

    private void calculateExternalWallArea(WallMeasurementEntity entity) {
        // Calcula a área total das janelas
        double totalWindowArea = entity.getWindows().stream()
                .mapToDouble(window -> window.getWindowsWidthFoot() * window.getWindowsHeightFoot())
                .sum();

        // Calcula a área total das portas
        double totalDoorArea = entity.getDoors().stream()
                .mapToDouble(door -> door.getDoorWidthFoot() * door.getDoorHeightFoot())
                .sum();

        // Calcula a área utilizável da parede
        double usableWallArea = (entity.getArea() - totalWindowArea - totalDoorArea) * 1.1; // Modificador de exemplo
        entity.setAreaToCalculate(usableWallArea);

        // Converte o comprimento da parede de pés para polegadas
        double wallLengthInInches = entity.getWallLengthFoot() * 12; // 1 pé = 12 polegadas

        // Calcula a quantidade de madeiras 2x6 necessárias
        double studSpacing = entity.getStudSpacing(); // Espaçamento entre os studs em polegadas
        double studWidth = 1.5; // Largura real da madeira 2x6 em polegadas
        double studDepth = 5.5; // Profundidade real da madeira 2x6 em polegadas

        // Calcula o número de espaços entre os studs
        int numberOfSpaces = (int) Math.ceil((wallLengthInInches - studWidth) / studSpacing);

        // O número total de studs é o número de espaços mais um (para o último stud)
        int numberOfStuds = numberOfSpaces + 1;

        entity.setNumberOfStuds(numberOfStuds); // Define o número de madeiras na entidade
    }


    private void calculateInternalWallArea(WallMeasurementEntity entity) {
        // Calcula a área total das janelas
        double totalWindowArea = entity.getWindows().stream()
                .mapToDouble(window -> window.getWindowsWidthFoot() * window.getWindowsHeightFoot())
                .sum();

        // Calcula a área total das portas
        double totalDoorArea = entity.getDoors().stream()
                .mapToDouble(door -> door.getDoorWidthFoot() * door.getDoorHeightFoot())
                .sum();

        // Calcula a área utilizável da parede
        double usableWallArea = entity.getArea() - totalWindowArea - totalDoorArea;
        entity.setAreaToCalculate(usableWallArea);

        // Converte o comprimento da parede de pés para polegadas
        double wallLengthInInches = entity.getWallLengthFoot() * 12; // 1 pé = 12 polegadas

        // Calcula a quantidade de madeiras 2x4 necessárias
        double studSpacing = entity.getStudSpacing(); // Espaçamento entre os studs em polegadas
        double studWidth = 1.5; // Largura real da madeira 2x4 em polegadas

        // Calcula o número de espaços entre os studs
        int numberOfSpaces = (int) Math.ceil((wallLengthInInches - studWidth) / studSpacing);

        // O número total de studs é o número de espaços mais um (para o último stud)
        int numberOfStuds = numberOfSpaces + 1;

        entity.setNumberOfStuds(numberOfStuds); // Define o número de madeiras na entidade
    }
}