package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Assumindo que pode ser auditável
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator; // Importar

import java.util.Objects; // Importar
import java.util.UUID;    // Importar

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "baseboards")
public class BaseboardEntity extends AuditableBaseEntity { // <<< Considerar auditar

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(length = 100) // Adicionar length
    private String material;
    private double heightInches;
    @Column(length = 50) // Adicionar length
    private String paintColor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BaseboardEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}