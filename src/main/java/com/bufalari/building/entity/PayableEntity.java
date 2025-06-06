package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.enums.PayableStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "project_payables_cache")
public class PayableEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "original_payable_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID originalPayableId;

    @Column(name = "supplier_id", nullable = false, columnDefinition = "uuid")
    private UUID supplierId;

    @Column(name = "project_id", nullable = false, columnDefinition = "uuid")
    private UUID projectId;

    @Column(name = "cost_center_id")
    private Long costCenterId;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(length = 100)
    private String invoiceReference;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate paymentDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountDue;

    @Column(precision = 15, scale = 2)
    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PayableStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_payable_doc_refs_cache", joinColumns = @JoinColumn(name = "payable_cache_id"))
    @Column(name = "document_reference", length = 500)
    @Builder.Default
    private List<String> documentReferences = new ArrayList<>();

    @PrePersist
    private void setDefaults() {
        if (status == null) {
            status = PayableStatus.PENDING;
        }
        if (amountPaid == null) {
            amountPaid = BigDecimal.ZERO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayableEntity that = (PayableEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}