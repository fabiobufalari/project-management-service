package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastenerDTO {
    private double nailsPounds;
    private double screwsPounds;
    private String codeReference;
}
