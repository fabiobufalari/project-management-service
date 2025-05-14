package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal; // Importar BigDecimal
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "balconies")
public class BalconyEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    private boolean hasBalcony;

    @Column(name = "balcony_area_square_feet") // double sem precision/scale
    private double balconyAreaSquareFeet;

    @Column(length = 100)
    private String railingMaterial;

    @Column(length = 100)
    private String floorMaterial;

    @Column(length = 100)
    private String structureType;

    @Column(name = "estimated_cost_per_square_foot", precision = 19, scale = 4)
    private BigDecimal estimatedCostPerSquareFoot; // ALTERADO para BigDecimal

    @Column(name = "total_estimated_cost", precision = 19, scale = 2)
    private BigDecimal totalEstimatedCost; // ALTERADO para BigDecimal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalconyEntity that = (BalconyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}