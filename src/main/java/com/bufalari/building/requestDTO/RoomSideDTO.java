package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomSideDTO {
    private String roomName;
    private boolean isWetArea;
    private String sideOfWall; // Ex: "Left", "Right"
    private String roomType;

}