package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDTO {
    private Long projectId;
    private String projectName;
    private String dateTime;
    private LocationDTO location;
    private String buildingType;
    private int numberOfFloors;
    private boolean hasBasement;

    // Inclusão da lista de CalculationStructureDTO
    private List<CalculationStructureDTO> calculationStructure;
}