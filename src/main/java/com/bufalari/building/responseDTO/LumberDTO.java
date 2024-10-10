package com.bufalari.building.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LumberDTO {
    private double totalBoardFeet;
    private List<String> availableTypes;
    private String codeReference;
}
