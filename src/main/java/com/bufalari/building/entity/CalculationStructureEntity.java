package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal; // Importar
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "floor_calculation_structures")
public class CalculationStructureEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    private int floorNumber;

    @Column(name = "area_square_feet") // Removido precision/scale para double
    private double areaSquareFeet;

    private boolean heated;

    @Column(length = 100)
    private String material;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_entity_id", foreignKey = @ForeignKey(name = "fk_calcstruct_floor"))
    private FloorEntity floor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_structure_id", foreignKey = @ForeignKey(name = "fk_wall_calcstruct"))
    @Builder.Default
    private List<WallEntity> walls = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ceiling_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_ceiling"))
    private CeilingEntity ceiling;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "baseboard_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_calcstruct_baseboard"))
    private BaseboardEntity baseboards;

    // ... (outras relações OneToOne com Painting, Balcony, etc.)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationStructureEntity that = (CalculationStructureEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}