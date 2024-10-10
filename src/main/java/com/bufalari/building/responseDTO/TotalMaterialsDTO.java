package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalMaterialsDTO {
    private LumberDTO lumber;
    private DrywallDTO drywall;
    private ConcreteDTO concrete;
    private InsulationDTO insulation;
    private FastenerDTO nailsAndScrews;
}
