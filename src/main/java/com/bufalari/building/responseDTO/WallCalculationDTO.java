package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallCalculationDTO {
    private String wallIdentification;
    private String description;
    private String sideOfWall; // External or Internal
    private double totalWallLengthFoot;
    private double wallThicknessInch;
    private boolean isWetArea;
    private int numberOfPlates;
    private int numberOfHeaders;
    private int numberOfPlywoodSheets;
    private int numberOfDrywallSheets;
    private String materialType;
    private List<StudDTO> studs;
    private List<WindowCalculationDTO> windows;
    private List<DoorCalculationDTO> doors;
    private String codeReference;
}
