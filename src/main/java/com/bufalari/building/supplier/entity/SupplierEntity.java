package com.bufalari.building.supplier.entity;


import com.bufalari.building.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "suppliers")
public class SupplierEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name; // Legal name / Razão Social

    @Column(length = 200)
    private String tradeName; // Trading name / Nome Fantasia

    @Column(unique = true, length = 50)
    private String businessIdentificationNumber; // CNPJ, Business Number, etc.

    @Embedded // Reuse address entity if defined commonly or define here
    private AddressEmbeddable address;

    @Column(length = 50)
    private String primaryContactName;

    @Column(length = 30)
    private String primaryContactPhone;

    @Column(length = 100)
    private String primaryContactEmail;

    @Column(length = 50)
    private String category; // e.g., MATERIAL, SERVICE, EQUIPMENT_RENTAL / Ex: MATERIAL, SERVIÇO, ALUGUEL_EQUIPAMENTO

    @Column(length = 50)
    private String bankName;

    @Column(length = 20)
    private String bankAgency;

    @Column(length = 30)
    private String bankAccount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "supplier_document_references", joinColumns = @JoinColumn(name = "supplier_id"))
    @Column(name = "document_reference")
    private List<String> documentReferences; // Links to document-storage-service
}

// Define AddressEmbeddable in a separate file or here if only used locally
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class AddressEmbeddable {
    private String street;
    private String number;
    private String complement;
    private String neighbourhood;
    private String city;
    private String province;
    private String postalCode;
    private String country;
}