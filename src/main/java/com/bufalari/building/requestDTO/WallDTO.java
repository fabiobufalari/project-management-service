package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallDTO {
    private String wallId;
    private String description;
    private String type;  // external or internal
    private double lengthFoot;
    private int lengthInches;
    private double heightFoot;
    private int heightInches;
    private double wallThicknessInch;
    private String material;
    private boolean isWetArea;
    private int studSpacingInch;
    private List<WindowDTO> windows;
    private List<DoorDTO> doors;
}