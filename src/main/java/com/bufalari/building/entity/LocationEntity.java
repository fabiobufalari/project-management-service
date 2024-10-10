package com.bufalari.building.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String city;
    private String province;
    private String postalCode;

    // Construtor sem o campo ID
    public LocationEntity(String address, String city, String province, String postalCode) {
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}
