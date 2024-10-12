package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudCalculationResultDTO {
    private String wallId;
    private int studCount;
    private double studLinearFootage;
}