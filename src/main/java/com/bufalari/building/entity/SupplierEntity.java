package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "suppliers_cache", indexes = { // Renomear tabela para indicar cache
    @Index(name = "idx_supplier_cache_name", columnList = "name"),
    @Index(name = "idx_supplier_cache_bin", columnList = "businessIdentificationNumber", unique = true)
})
public class SupplierEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id; // ID da cópia local

    @Column(name = "original_supplier_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID originalSupplierId; // ID do supplier no supplier-service

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 200)
    private String tradeName;

    @Column(unique = true, length = 50)
    private String businessIdentificationNumber;

    @Embedded
    private AddressEmbeddable address;

    @Column(length = 100)
    private String primaryContactName;

    @Column(length = 50)
    private String primaryContactPhone;

    @Column(length = 150)
    private String primaryContactEmail;

    @Column(length = 50)
    private String category;

    @Column(length = 100)
    private String bankName;

    @Column(length = 20)
    private String bankAgency;

    @Column(length = 30)
    private String bankAccount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "supplier_cache_doc_refs",
                     joinColumns = @JoinColumn(name = "supplier_cache_id", foreignKey = @ForeignKey(name = "fk_suppcachedoc_suppliercache")))
    @Column(name = "document_reference", length = 500)
    @Builder.Default
    private List<String> documentReferences = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierEntity that = (SupplierEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}