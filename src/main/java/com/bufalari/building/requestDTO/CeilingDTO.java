package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CeilingDTO {
    private double heightFoot;
    private String material;
    private String paintColor;
}
