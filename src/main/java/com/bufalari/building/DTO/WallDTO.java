package com.bufalari.building.DTO;

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
    private String type; // "external", "internal"
    private double lengthFoot;
    private double lengthInches;
    private double heightFoot;
    private double heightInches;
    private double wallThicknessInch;
    private List<RoomSideDTO> roomSides; // Informações sobre os cômodos em cada lado da parede
    private int floorNumber;
}