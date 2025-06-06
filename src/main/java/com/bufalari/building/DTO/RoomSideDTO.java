package com.bufalari.building.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSideDTO {
    private String roomType;
    private boolean isWetArea;
    private String sideOfWall;

    // Método para acessar o atributo roomType
    public String getRoomType() {
        return roomType;
    }

    public String getSideOfWall() {
        return sideOfWall;
    }
}