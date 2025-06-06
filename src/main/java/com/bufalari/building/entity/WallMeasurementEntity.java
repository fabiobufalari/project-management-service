package com.bufalari.building.entity;

import com.bufalari.building.enums.SideOfWall;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "model_wall_measurements") // Nome de tabela para evitar conflito
public class WallMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mantém Long ID
    private Long id;

    @Column(name = "calculated_area_sqft") // Nome de coluna mais descritivo
    private Double area; // Área total em pés quadrados.

    @Column(length = 100)
    private String identifyWall; // Identificador textual
    private Double wallLengthFoot;
    private Double wallLengthInches; // Parte em polegadas
    private Double wallHeightFoot;
    private Double wallHeightInches; // Parte em polegadas
    private Double wallThickness;    // Espessura da parede
    private Double studSpacing;      // Espaçamento entre montantes (studs)

    @Enumerated(EnumType.STRING)
    @Column(length = 30) // Ajustar tamanho para os valores do Enum
    private SideOfWall sideOfWall; // Lado da parede (interno/externo)

    // Relações com as dimensões de janelas e portas (deste modelo)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "wallMeasurement", fetch = FetchType.LAZY)
    private List<WindowDimensionsEntity> windows = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "wallMeasurement", fetch = FetchType.LAZY)
    private List<DoorDimensionsEntity> doors = new ArrayList<>();

    // O campo 'areaToCalculate' foi removido, pois 'area' já representa a área calculada.
    // Se 'areaToCalculate' fosse a área *utilizável* (descontando vãos),
    // precisaria de um nome mais claro, como 'usableAreaSqFt'.
    // private double areaToCalculate;

    // O campo 'numberOfStuds' foi removido. Se necessário, deve ser parte de um DTO de resultado de cálculo,
    // ou se persistido, ter um nome claro como 'calculatedStudCount'.
    // private int numberOfStuds;

    // Construtor, getters, setters, etc., gerados pelo Lombok @Data
}