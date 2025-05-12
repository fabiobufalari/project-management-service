package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Assumindo que pode ser auditável
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator; // Importar

import java.util.Objects; // Importar
import java.util.UUID;    // Importar

@Entity
@Getter // Usar Getter/Setter individuais ou @Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Adicionar Builder
@Table(name = "balconies") // Nome da tabela
public class BalconyEntity extends AuditableBaseEntity { // <<< Considerar auditar

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    private boolean hasBalcony;
    private double balconyAreaSquareFeet;
    @Column(length = 100) // Adicionar length
    private String railingMaterial;
    @Column(length = 100) // Adicionar length
    private String floorMaterial;
    @Column(length = 100) // Adicionar length
    private String structureType;
    private double estimatedCostPerSquareFoot;
    private double totalEstimatedCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BalconyEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}