package com.bufalari.building.service;

import com.bufalari.building.enums.SideOfWall;
import com.bufalari.building.model.WallMeasurementEntity;
import com.bufalari.building.repository.WallMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bufalari.building.util.ConvertFeetToInches.convertFeetToInches;

@Service
public class WallMeasurementService {

    @Autowired
    private WallMeasurementRepository repository;

    @Transactional
    public WallMeasurementEntity calculateAndSaveMeasurement(WallMeasurementEntity entity) {
        // Validate input
        validateWallMeasurements(entity);

        // Calculate wall area
        double area = calculateWallArea(entity);
        entity.setArea(area);

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
        if (entity.getWallLength() <= 0 || entity.getWallHeight() <= 0 || entity.getWallThickness() <= 0) {
            throw new IllegalArgumentException("Wall dimensions must be greater than zero.");
        }
    }

    public double calculateWallArea(WallMeasurementEntity entity) {
        return entity.getWallLength() * entity.getWallHeight();
    }

   /* private void calculateInternalWallArea(WallMeasurementEntity entity) {
        // Example logic for internal wall calculations
        double totalWindowArea = entity.getWindows().stream()
                .mapToDouble(window -> window.getWindowsWidth() * window.getWindowsHeight())
                .sum();
        double totalDoorArea = entity.getDoors().stream()
                .mapToDouble(door -> door.getDoorWidth() * door.getDoorHeight())
                .sum();
        double usableWallArea = entity.getArea() - totalWindowArea - totalDoorArea;
        entity.setAreaToCalculate(usableWallArea); // Example field, you'd add this to your entity
    }

*/



    private void calculateExternalWallArea(WallMeasurementEntity entity) {
        // Example logic for external wall calculations
        double totalWindowArea = entity.getWindows().stream()
                .mapToDouble(window -> window.getWindowsWidth() * window.getWindowsHeight())
                .sum();
        double totalDoorArea = entity.getDoors().stream()
                .mapToDouble(door -> door.getDoorWidth() * door.getDoorHeight())
                .sum();
        // Maybe external walls have different insulation or additional layers
        double externalWallModifier = 1.1; // Example modifier
        double usableWallArea = (entity.getArea() - totalWindowArea - totalDoorArea) * externalWallModifier;
        entity.setAreaToCalculate(usableWallArea); // Example field, you'd add this to your entity
    }

    //aqui
    private void calculateInternalWallArea(WallMeasurementEntity entity) {
        // Calcula a área total das janelas
        double totalWindowArea = entity.getWindows().stream()
                .mapToDouble(window -> window.getWindowsWidth() * window.getWindowsHeight())
                .sum();

        // Calcula a área total das portas
        double totalDoorArea = entity.getDoors().stream()
                .mapToDouble(door -> door.getDoorWidth() * door.getDoorHeight())
                .sum();

        // Calcula a área utilizável da parede
        double usableWallArea = entity.getArea() - totalWindowArea - totalDoorArea;
        entity.setAreaToCalculate(usableWallArea);

        // Converte o comprimento da parede de pés para polegadas
        double wallLengthInInches = entity.getWallLength() * 12; // 1 pé = 12 polegadas

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