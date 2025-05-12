package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.enums.PayableStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator; // Importar

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList; // Importar
import java.util.List;
import java.util.Objects;
import java.util.UUID; // <<<--- IMPORT UUID

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "project_payables_cache") // Nome de tabela para indicar que pode ser uma cópia/cache
public class PayableEntity extends AuditableBaseEntity {

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID (ID da cópia local/cache)

    @Column(name = "original_payable_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID originalPayableId; // <<<--- UUID do Payable no serviço de origem

    @Column(nullable = false, columnDefinition = "uuid") // Se Supplier ID for UUID no serviço de Supplier
    private UUID supplierId; // <<<--- UUID (assumindo que supplier service usa UUID)

    @Column(name = "project_id", nullable = false, columnDefinition = "uuid") // Assumindo que este projeto usa UUID
    private UUID projectId; // <<<--- UUID (ID do projeto neste serviço)

    @Column(name = "cost_center_id")
    private Long costCenterId; // Mantém Long se o serviço de centro de custo usar Long

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
        if (o == null || !(o instanceof PayableEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}