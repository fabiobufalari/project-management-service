package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionDTO {
    private double widthFoot;
    private int widthInches;
    private double heightFoot;
    private int heightInches;
    private double thicknessInch;
}
