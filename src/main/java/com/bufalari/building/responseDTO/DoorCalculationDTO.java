package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoorCalculationDTO {
    private String doorId;
    private double doorWidthFoot;
    private int doorWidthInches;
    private double doorHeightFoot;
    private int doorHeightInches;
    private double doorThicknessInch;
    private String codeReference;
}
