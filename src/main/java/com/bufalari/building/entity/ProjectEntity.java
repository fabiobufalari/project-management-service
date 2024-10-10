package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<FloorEntity> floors;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<HouseOwner> owners;
}
