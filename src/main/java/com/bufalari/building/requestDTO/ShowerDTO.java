package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowerDTO {
    private String type;
    private String material;
    private double heightFoot;
}
