package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenAccessoriesDTO {
    private SinkDTO sink;
    private CountertopDTO countertop;
    private CabinetsDTO cabinets;
}
