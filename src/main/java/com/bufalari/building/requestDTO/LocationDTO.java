package com.bufalari.building.requestDTO;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
