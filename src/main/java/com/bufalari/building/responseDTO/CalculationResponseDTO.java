package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponseDTO {
    private List<WallCalculationDTO> calculationExternal;
    private List<WallCalculationDTO> calculationInternal;
    private RoofCalculationDTO roof;
    private BalconyCalculationDTO balcony;
    private TotalMaterialsDTO totalMaterials;
}