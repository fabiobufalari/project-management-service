package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity; // Assumindo auditável
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "showers")
public class ShowerEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(length = 100)
    private String type; // Ex: "Box", "Walk-in"
    @Column(length = 100)
    private String material; // Ex: "Glass", "Tile"
    private double heightFoot; // Altura em pés

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ShowerEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}