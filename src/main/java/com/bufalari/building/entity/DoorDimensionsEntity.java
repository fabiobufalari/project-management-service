package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "model_door_dimensions") // Nome de tabela para evitar conflito com entity.DoorEntity
public class DoorDimensionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mantém Long ID
    private Long id;

    private Double doorWidthFoot;
    private Double doorWidthInches; // Assumindo que armazena a parte em polegadas como double
    private Double doorHeightFoot;
    private Double doorHeightInches; // Assumindo que armazena a parte em polegadas como double
    private Double doorThickness;

    @ManyToOne(fetch = FetchType.LAZY) // Relação com WallMeasurementEntity
    @JoinColumn(name = "wall_measurement_id", foreignKey = @ForeignKey(name = "fk_modeldoor_wallmeasurement"))
    private WallMeasurementEntity wallMeasurement;
}