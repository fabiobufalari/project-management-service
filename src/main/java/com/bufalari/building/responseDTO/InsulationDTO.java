package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsulationDTO {
    private double totalSquareFeet;
    private String rValue;
    private String codeReference;
}
