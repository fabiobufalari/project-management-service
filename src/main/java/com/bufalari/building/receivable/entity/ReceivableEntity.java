// Path: accounts-receivable-service/src/main/java/com/bufalari/receivable/entity/ReceivableEntity.java
package com.bufalari.building.receivable.entity;


import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.receivable.enums.ReceivableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "receivables")
public class ReceivableEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifier of the client this receivable is due from (linking to client-service).
     * Identificador do cliente de quem esta conta a receber é devida (ligando ao client-service).
     */
    @Column(nullable = false)
    private UUID clientId; // Assuming client service uses UUIDs

    /**
     * Identifier of the project this receivable relates to (linking to project-management-service).
     * Identificador do projeto ao qual esta conta a receber se refere (ligando ao project-management-service).
     */
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * Description of the receivable item or service rendered.
     * Descrição do item a receber ou serviço prestado.
     */
    @Column(nullable = false, length = 255)
    private String description;

    /**
     * Reference number for the invoice sent to the client.
     * Número de referência da fatura enviada ao cliente.
     */
    @Column(length = 100)
    private String invoiceReference;

    /**
     * Date the receivable (invoice) was issued.
     * Data em que a conta a receber (fatura) foi emitida.
     */
    @Column(nullable = false)
    private LocalDate issueDate;

    /**
     * Due date for the payment from the client.
     * Data de vencimento do pagamento pelo cliente.
     */
    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Date the payment was actually received.
     * Data em que o pagamento foi efetivamente recebido.
     */
    private LocalDate receivedDate;

    /**
     * Amount expected to be received.
     * Valor esperado para recebimento.
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountExpected;

    /**
     * Amount actually received (might differ due to deductions, partial payments).
     * Valor efetivamente recebido (pode diferir devido a deduções, pagamentos parciais).
     */
    @Column(precision = 15, scale = 2)
    private BigDecimal amountReceived;

    /**
     * Current status of the receivable.
     * Status atual da conta a receber.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReceivableStatus status;

    /**
     * Reason why the payment is pending or blocked (if applicable). Important for recovery focus.
     * Motivo pelo qual o pagamento está pendente ou bloqueado (se aplicável). Importante para o foco na recuperação.
     */
    @Column(name = "blocker_reason", length = 500)
    private String blockerReason;

    /**
     * References to supporting documents (invoices, contracts, measurement proof).
     * Referências a documentos de suporte (faturas, contratos, comprovantes de medição).
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "receivable_document_references", joinColumns = @JoinColumn(name = "receivable_id"))
    @Column(name = "document_reference")
    private List<String> documentReferences; // Links to document-storage-service

    @PrePersist
    private void setDefaults() {
        if (status == null) {
            status = ReceivableStatus.PENDING;
        }
        if (amountReceived == null) {
            amountReceived = BigDecimal.ZERO;
        }
    }
}