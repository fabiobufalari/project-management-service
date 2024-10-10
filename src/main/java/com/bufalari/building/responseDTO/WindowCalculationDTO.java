package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindowCalculationDTO {
    private String windowId;
    private double windowWidthFoot;
    private int windowWidthInches;
    private double windowHeightFoot;
    private int windowHeightInches;
    private double windowThicknessInch;
    private String codeReference;
}
