package com.bufalari.building.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar se não existir

@Data
@NoArgsConstructor // Adicionar
@Entity
@Table(name = "model_window_dimensions") // Nome de tabela para evitar conflito
public class WindowDimensionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mantém Long ID
    private Long id;

    private Double windowsWidthFoot;
    private Double windowsWidthInches; // Assumindo que armazena a parte em polegadas como double
    private Double windowsHeightFoot;
    private Double windowsHeightInches; // Assumindo que armazena a parte em polegadas como double
    private Double windowsThickness;

    @ManyToOne(fetch = FetchType.LAZY) // Relação com WallMeasurementEntity
    @JoinColumn(name = "wall_measurement_id", foreignKey = @ForeignKey(name = "fk_modelwindow_wallmeasurement"))
    private WallMeasurementEntity wallMeasurement;
}