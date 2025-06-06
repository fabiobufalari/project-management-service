package com.bufalari.building.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BathroomAccessoriesDTO {
    private ShowerDTO shower;
    private SinkDTO sink;
    private ToiletDTO toilet;
}
