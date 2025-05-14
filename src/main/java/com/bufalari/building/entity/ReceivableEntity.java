package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.enums.ReceivableStatus;
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
@Table(name = "project_receivables_cache")
public class ReceivableEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "original_receivable_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID originalReceivableId;

    @Column(name = "client_id", nullable = false, columnDefinition = "uuid")
    private UUID clientId;

    @Column(name = "project_id", nullable = false, columnDefinition = "uuid")
    private UUID projectId;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(length = 100)
    private String invoiceReference;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate receivedDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountExpected;

    @Column(precision = 15, scale = 2)
    private BigDecimal amountReceived;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReceivableStatus status;

    @Column(name = "blocker_reason", length = 500)
    private String blockerReason;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_receivable_doc_refs_cache", joinColumns = @JoinColumn(name = "receivable_cache_id"))
    @Column(name = "document_reference", length = 500)
    @Builder.Default
    private List<String> documentReferences = new ArrayList<>();

    @PrePersist
    private void setDefaults() {
        if (status == null) {
            status = ReceivableStatus.PENDING;
        }
        if (amountReceived == null) {
            amountReceived = BigDecimal.ZERO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceivableEntity that = (ReceivableEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}