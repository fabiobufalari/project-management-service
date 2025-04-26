package com.bufalari.building.payable.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.payable.enums.PayableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payables")
public class PayableEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifier of the supplier this payable is owed to (linking to supplier-service).
     * Identificador do fornecedor a quem esta conta a pagar é devida (ligando ao supplier-service).
     */
    @Column(nullable = false)
    private Long supplierId;

    /**
     * Identifier of the project this payable is allocated to (linking to project-management-service). Null if allocated to a cost center.
     * Identificador do projeto ao qual esta conta a pagar é alocada (ligando ao project-management-service). Nulo se alocado a um centro de custo.
     */
    @Column(name = "project_id")
    private Long projectId;

    /**
     * Identifier for the cost center this payable is allocated to (e.g., rent, utilities). Null if allocated to a project.
     * Identificador para o centro de custo ao qual esta conta a pagar é alocada (ex: aluguel, utilidades). Nulo se alocado a um projeto.
     */
    @Column(name = "cost_center_id")
    private Long costCenterId;

    /**
     * Description of the payable item or service.
     * Descrição do item ou serviço a pagar.
     */
    @Column(nullable = false, length = 255)
    private String description;

    /**
     * Reference number for the invoice or bill.
     * Número de referência da fatura ou boleto.
     */
    @Column(length = 100)
    private String invoiceReference;

    /**
     * Date the payable was issued or registered.
     * Data em que a conta a pagar foi emitida ou registrada.
     */
    @Column(nullable = false)
    private LocalDate issueDate;

    /**
     * Due date for the payment.
     * Data de vencimento do pagamento.
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Date the payment was actually made.
     * Data em que o pagamento foi efetivamente realizado.
     */
    private LocalDate paymentDate;

    /**
     * Amount due.
     * Valor devido.
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountDue;

    /**
     * Amount actually paid (might differ due to discounts, partial payments).
     * Valor efetivamente pago (pode diferir devido a descontos, pagamentos parciais).
     */
    @Column(precision = 15, scale = 2)
    private BigDecimal amountPaid;

    /**
     * Current status of the payable.
     * Status atual da conta a pagar.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PayableStatus status;

    /**
     * References to supporting documents (invoice scans, receipts).
     * Referências a documentos de suporte (scans de faturas, recibos).
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "payable_document_references", joinColumns = @JoinColumn(name = "payable_id"))
    @Column(name = "document_reference")
    private List<String> documentReferences; // Links to document-storage-service

    @PrePersist
    private void setDefaults() {
        if (status == null) {
            status = PayableStatus.PENDING;
        }
        if (amountPaid == null) {
            amountPaid = BigDecimal.ZERO;
        }
    }
}