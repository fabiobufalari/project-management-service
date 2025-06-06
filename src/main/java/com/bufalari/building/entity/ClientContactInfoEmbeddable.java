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
public class ClientContactInfoEmbeddable {

    @Column(name = "contact_phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "contact_email", length = 100)
    private String email;
}