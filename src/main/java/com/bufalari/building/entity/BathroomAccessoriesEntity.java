package com.bufalari.building.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BathroomAccessoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shower_id", referencedColumnName = "id")
    private ShowerEntity shower;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sink_id", referencedColumnName = "id")
    private SinkEntity sink;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "toilet_id", referencedColumnName = "id")
    private ToiletEntity toilet;
}
