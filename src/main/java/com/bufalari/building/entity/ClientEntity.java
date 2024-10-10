package com.bufalari.building.entity;

import com.bufalari.building.DTO.ClientContactInfoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    @Embedded
    private ClientContactInfoDTO contactInfo;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<HouseOwner> houseOwners;
}
