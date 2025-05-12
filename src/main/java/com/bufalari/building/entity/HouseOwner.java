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
@Table(name = "house_owners", indexes = { // Índices para as FKs
    @Index(name = "idx_houseowner_project_id", columnList = "project_id"),
    @Index(name = "idx_houseowner_client_id", columnList = "client_id")
})
public class HouseOwner extends AuditableBaseEntity { // Auditável

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    // Relação com Projeto (Muitos Proprietários para Um Projeto, ou Um Proprietário para Um Projeto se for 1-1)
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Um registro de propriedade deve pertencer a um projeto
    @JoinColumn(name = "project_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_houseowner_project"))
    private ProjectEntity project; // Referencia ProjectEntity (com ID UUID)

    // Relação com Cliente (Muitos Registros de Propriedade para Um Cliente, se um cliente pode ter várias propriedades)
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Um registro de propriedade deve pertencer a um cliente
    @JoinColumn(name = "client_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_houseowner_client"))
    private ClientEntity client; // Referencia ClientEntity (com ID UUID)

    @Column(precision = 5, scale = 2) // Ex: 100.00 para 100%
    private Double ownershipPercentage; // Percentual de propriedade

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HouseOwner that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}