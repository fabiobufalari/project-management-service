package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.entity.AddressEmbeddable; // <<< Usar entidade embutível comum
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList; // Importar
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "suppliers", indexes = { // Adicionar índices
    @Index(name = "idx_supplier_name", columnList = "name"),
    @Index(name = "idx_supplier_bin", columnList = "businessIdentificationNumber", unique = true)
})
public class SupplierEntity extends AuditableBaseEntity {

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(nullable = false, length = 200)
    private String name; // Legal name / Razão Social

    @Column(length = 200)
    private String tradeName; // Trading name / Nome Fantasia

    @Column(unique = true, length = 50)
    private String businessIdentificationNumber; // CNPJ, Business Number, etc.

    @Embedded
    private AddressEmbeddable address; // <<< Usar AddressEmbeddable comum

    @Column(length = 100) // Aumentar tamanho
    private String primaryContactName;

    @Column(length = 50) // Aumentar tamanho
    private String primaryContactPhone;

    @Column(length = 150) // Aumentar tamanho
    private String primaryContactEmail;

    @Column(length = 50)
    private String category; // e.g., MATERIAL, SERVICE, EQUIPMENT_RENTAL

    @Column(length = 100)
    private String bankName;

    @Column(length = 20)
    private String bankAgency;

    @Column(length = 30)
    private String bankAccount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "supplier_document_references",
                     joinColumns = @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "fk_suppdoc_supplier")))
    @Column(name = "document_reference", length = 500)
    @Builder.Default
    private List<String> documentReferences = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SupplierEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}