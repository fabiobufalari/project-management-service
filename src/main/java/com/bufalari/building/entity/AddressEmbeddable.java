package com.bufalari.building.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEmbeddable {
    @Column(name = "address_street", length = 255)
    private String street;
    @Column(name = "address_number", length = 50)
    private String number;
    @Column(name = "address_complement", length = 100)
    private String complement;
    @Column(name = "address_neighbourhood", length = 100)
    private String neighbourhood;
    @Column(name = "address_city", length = 100)
    private String city;
    @Column(name = "address_province", length = 100)
    private String province;
    @Column(name = "address_postal_code", length = 20)
    private String postalCode;
    @Column(name = "address_country", length = 100)
    private String country;
}