package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;
    private String dateTime;
    private String buildingType;
    private int numberOfFloors;
    private boolean hasBasement;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity locationEntity;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<HouseOwner> owners;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<FloorEntity> floors;

}
