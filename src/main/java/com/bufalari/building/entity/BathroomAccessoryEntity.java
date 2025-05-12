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
@Table(name = "generic_bathroom_accessories") // Nome de tabela mais específico
public class BathroomAccessoryEntity extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(nullable = false, length = 100) // Tipo não nulo
    private String type; // Ex: "Towel Rack", "Soap Dispenser", "Mirror"

    @Column(length = 100)
    private String material;

    @Column // Altura pode não ser aplicável a todos os acessórios genéricos
    private Double height; // Usar Double para permitir valores decimais

    // Opcional: Referência ao conjunto de acessórios de banheiro principal, se este for um item adicional
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "bathroom_accessories_set_id")
    // private BathroomAccessoriesEntity bathroomAccessoriesSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof BathroomAccessoryEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}