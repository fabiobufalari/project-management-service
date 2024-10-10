package com.bufalari.building.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitchenAccessoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sink_id", referencedColumnName = "id")
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "countertop_id", referencedColumnName = "id")
    private CountertopEntity countertop;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cabinets_id", referencedColumnName = "id")
    private CabinetsEntity cabinets;
}
