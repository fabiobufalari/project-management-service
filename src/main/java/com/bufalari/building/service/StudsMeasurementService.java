package com.bufalari.building.service;

import com.bufalari.building.entity.DoorDimensionsEntity;
import com.bufalari.building.entity.WallMeasurementEntity;
import com.bufalari.building.entity.WindowDimensionsEntity;
import com.bufalari.building.enums.SideOfWall;
import com.bufalari.building.repository.WallMeasurementRepository;
import lombok.RequiredArgsConstructor; // Usar Lombok
import org.slf4j.Logger; // Logger
import org.slf4j.LoggerFactory; // Logger
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bufalari.building.util.UnitConversionUtils.convertInchesToFeet;


@Service
@RequiredArgsConstructor // Injeta WallMeasurementRepository
public class StudsMeasurementService {

    private static final Logger log = LoggerFactory.getLogger(StudsMeasurementService.class);
    private final WallMeasurementRepository repository;

    // Se TopPlateCalculationService fosse um bean, seria injetado:
    // private final TopPlateCalculationService topPlateCalculationService;

    @Transactional
    public WallMeasurementEntity calculateAndSaveMeasurement(WallMeasurementEntity entity) {
        log.debug("Calculating and saving measurements for wall: {}", entity.getIdentifyWall());
        // Validar entrada
        validateWallMeasurements(entity);

        // Garantir unidades consistentes (converter tudo para pés, por exemplo)
        ensureConsistentUnits(entity);

        // Calcular área da parede
        double area = calculateWallArea(entity);
        entity.setArea(area);
        log.debug("Calculated wall area: {} sq ft for wall: {}", area, entity.getIdentifyWall());

        // Lógica para calcular área utilizável e número de studs
        if (isInternalWall(entity)) {
            calculateInternalWallStuds(entity);
        } else {
            calculateExternalWallStuds(entity);
        }

        // Garantir que as relações bidirecionais sejam definidas se as entidades filhas tiverem a referência
        if (entity.getDoors() != null) {
            for (DoorDimensionsEntity door : entity.getDoors()) {
                door.setWallMeasurement(entity);
            }
        }
        if (entity.getWindows() != null) {
            for (WindowDimensionsEntity window : entity.getWindows()) {
                window.setWallMeasurement(entity);
            }
        }

        WallMeasurementEntity savedEntity = repository.save(entity);
        log.info("Saved WallMeasurementEntity with ID: {} for wall: {}", savedEntity.getId(), savedEntity.getIdentifyWall());
        return savedEntity;
    }

    private boolean isInternalWall(WallMeasurementEntity entity) {
        return entity.getSideOfWall() == SideOfWall.INTERNAL;
    }

    private void validateWallMeasurements(WallMeasurementEntity entity) {
        if (entity.getWallLengthFoot() == null || entity.getWallLengthFoot() <= 0 ||
            entity.getWallHeightFoot() == null || entity.getWallHeightFoot() <= 0) {
            log.error("Invalid wall dimensions provided for wall: {}. Length and height must be positive.", entity.getIdentifyWall());
            throw new IllegalArgumentException("Wall dimensions (length, height) must be greater than zero.");
        }
        if (entity.getWallThickness() == null || entity.getWallThickness() <= 0) {
            log.warn("Wall thickness not provided or not positive for wall: {}. Using default or calculations might be affected.", entity.getIdentifyWall());
            // Não lançar exceção aqui, mas considerar se é obrigatório
        }
        if (entity.getStudSpacing() == null || entity.getStudSpacing() <= 0) {
            log.error("Invalid stud spacing provided for wall: {}. Stud spacing must be positive.", entity.getIdentifyWall());
            throw new IllegalArgumentException("Stud spacing must be greater than zero.");
        }
    }

    private void ensureConsistentUnits(WallMeasurementEntity entity) {
        // Normaliza polegadas para 0.0 se forem nulas, antes de somar aos pés
        double lengthInches = entity.getWallLengthInches() != null ? entity.getWallLengthInches() : 0.0;
        double heightInches = entity.getWallHeightInches() != null ? entity.getWallHeightInches() : 0.0;

        // Garante que os pés não sejam nulos antes de somar
        double lengthFeet = entity.getWallLengthFoot() != null ? entity.getWallLengthFoot() : 0.0;
        double heightFeet = entity.getWallHeightFoot() != null ? entity.getWallHeightFoot() : 0.0;

        entity.setWallLengthFoot(lengthFeet + convertInchesToFeet(lengthInches));
        entity.setWallHeightFoot(heightFeet + convertInchesToFeet(heightInches));
        entity.setWallLengthInches(0.0); // Zera as polegadas após conversão para pés
        entity.setWallHeightInches(0.0); // Zera as polegadas

        if (entity.getWindows() != null) {
            entity.getWindows().forEach(window -> {
                double windowWidthInches = window.getWindowsWidthInches() != null ? window.getWindowsWidthInches() : 0.0;
                double windowHeightInches = window.getWindowsHeightInches() != null ? window.getWindowsHeightInches() : 0.0;
                double windowWidthFeet = window.getWindowsWidthFoot() != null ? window.getWindowsWidthFoot() : 0.0;
                double windowHeightFeet = window.getWindowsHeightFoot() != null ? window.getWindowsHeightFoot() : 0.0;

                window.setWindowsWidthFoot(windowWidthFeet + convertInchesToFeet(windowWidthInches));
                window.setWindowsHeightFoot(windowHeightFeet + convertInchesToFeet(windowHeightInches));
                window.setWindowsWidthInches(0.0);
                window.setWindowsHeightInches(0.0);
            });
        }

        if (entity.getDoors() != null) {
            entity.getDoors().forEach(door -> {
                double doorWidthInches = door.getDoorWidthInches() != null ? door.getDoorWidthInches() : 0.0;
                double doorHeightInches = door.getDoorHeightInches() != null ? door.getDoorHeightInches() : 0.0;
                double doorWidthFeet = door.getDoorWidthFoot() != null ? door.getDoorWidthFoot() : 0.0;
                double doorHeightFeet = door.getDoorHeightFoot() != null ? door.getDoorHeightFoot() : 0.0;

                door.setDoorWidthFoot(doorWidthFeet + convertInchesToFeet(doorWidthInches));
                door.setDoorHeightFoot(doorHeightFeet + convertInchesToFeet(doorHeightInches));
                door.setDoorWidthInches(0.0);
                door.setDoorHeightInches(0.0);
            });
        }
        log.trace("Ensured consistent units (feet) for wall: {}", entity.getIdentifyWall());
    }

    public double calculateWallArea(WallMeasurementEntity entity) {
        if (entity.getWallLengthFoot() == null || entity.getWallHeightFoot() == null) {
            log.error("Cannot calculate wall area for wall {}: length or height is null after unit conversion.", entity.getIdentifyWall());
            return 0.0; // Ou lançar exceção
        }
        return entity.getWallLengthFoot() * entity.getWallHeightFoot();
    }

    // Método para calcular área utilizável e número de studs para paredes externas
    private void calculateExternalWallStuds(WallMeasurementEntity entity) {
        // Calcula a área total das janelas
        double totalWindowArea = entity.getWindows() != null ? entity.getWindows().stream()
                .filter(w -> w.getWindowsWidthFoot() != null && w.getWindowsHeightFoot() != null)
                .mapToDouble(window -> window.getWindowsWidthFoot() * window.getWindowsHeightFoot())
                .sum() : 0.0;

        // Calcula a área total das portas
        double totalDoorArea = entity.getDoors() != null ? entity.getDoors().stream()
                .filter(d -> d.getDoorWidthFoot() != null && d.getDoorHeightFoot() != null)
                .mapToDouble(door -> door.getDoorWidthFoot() * door.getDoorHeightFoot())
                .sum() : 0.0;

        // Calcula a área utilizável da parede
        // A lógica de `* 1.1` para área utilizável pode precisar de revisão/clarificação.
        double usableWallArea = (entity.getArea() - totalWindowArea - totalDoorArea); // Removido * 1.1 por enquanto
        // Se o fator de 10% for para desperdício de material, ele deve ser aplicado no cálculo do material, não na área em si.
        // entity.setAreaToCalculate(usableWallArea); // Se este campo for persistido e representar área líquida

        // Converte o comprimento da parede para polegadas para cálculo de studs
        double wallLengthInInches = entity.getWallLengthFoot() * 12.0;

        // Calcula a quantidade de madeiras (studs)
        double studSpacing = entity.getStudSpacing(); // Espaçamento entre os studs em polegadas
        // double studWidth = 1.5; // Largura real da madeira (ex: 2x6 -> 1.5 polegadas). Pode variar.

        // O número total de studs é geralmente (comprimento / espaçamento) + 1 (para o último)
        // Se a parede começar e terminar com um stud.
        int numberOfStuds = (int) Math.ceil(wallLengthInInches / studSpacing) + 1;

        // entity.setNumberOfStuds(numberOfStuds); // Se este campo for persistido
        log.debug("Calculated for external wall {}: UsableArea={}, StudCount={}", entity.getIdentifyWall(), usableWallArea, numberOfStuds);
    }

    // Método para calcular área utilizável e número de studs para paredes internas
    private void calculateInternalWallStuds(WallMeasurementEntity entity) {
        double totalWindowArea = entity.getWindows() != null ? entity.getWindows().stream()
                .filter(w -> w.getWindowsWidthFoot() != null && w.getWindowsHeightFoot() != null)
                .mapToDouble(window -> window.getWindowsWidthFoot() * window.getWindowsHeightFoot())
                .sum() : 0.0;
        double totalDoorArea = entity.getDoors() != null ? entity.getDoors().stream()
                .filter(d -> d.getDoorWidthFoot() != null && d.getDoorHeightFoot() != null)
                .mapToDouble(door -> door.getDoorWidthFoot() * door.getDoorHeightFoot())
                .sum() : 0.0;

        double usableWallArea = entity.getArea() - totalWindowArea - totalDoorArea;
        // entity.setAreaToCalculate(usableWallArea);

        double wallLengthInInches = entity.getWallLengthFoot() * 12.0;
        double studSpacing = entity.getStudSpacing();
        // double studWidth = 1.5; // Largura real da madeira (ex: 2x4 -> 1.5 polegadas)

        int numberOfStuds = (int) Math.ceil(wallLengthInInches / studSpacing) + 1;
        // entity.setNumberOfStuds(numberOfStuds);
        log.debug("Calculated for internal wall {}: UsableArea={}, StudCount={}", entity.getIdentifyWall(), usableWallArea, numberOfStuds);
    }
}