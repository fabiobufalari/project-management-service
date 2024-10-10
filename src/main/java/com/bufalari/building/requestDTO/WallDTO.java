package com.bufalari.building.requestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// WallDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallDTO {
    private Long wallId;
    private String description;
    private String type;
    private double lengthFoot;
    private double lengthInches;
    private double heightFoot;
    private double heightInches;
    private double wallThicknessInch;
    private String material;
    private boolean isExternal; // Indica se a parede é externa

    private double height;
    private double length;
    private String materialType;
    private List<RoomSideDTO> roomSides;

    private List<WindowDTO> windows;
    private List<DoorDTO> doors;
}