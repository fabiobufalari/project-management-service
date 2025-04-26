package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.enums.ProjectStatus;      // Import status enum
import jakarta.persistence.*;
import lombok.*; // Use consistent Lombok annotations

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a construction project with financial and management details.
 * Entidade que representa um projeto de construção com detalhes financeiros e de gerenciamento.
 */
@Entity
@Getter                 // Use individual annotations for better control
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder                // Add Builder pattern
@Table(name = "projects") // Plural table name convention
public class ProjectEntity extends AuditableBaseEntity { // Inherit auditing fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User-friendly name for the project.
     * Nome amigável para o projeto.
     */
    @Column(nullable = false, length = 200)
    private String projectName;

    /**
     * Date and time the project record was initially created or registered in the system.
     * Data e hora em que o registro do projeto foi inicialmente criado ou registrado no sistema.
     */
    private LocalDateTime dateTime; // Consider renaming to registrationDateTime for clarity

    /**
     * Type of building being constructed (e.g., House, Apartment Building, Renovation).
     * Tipo de edificação sendo construída (ex: Casa, Prédio de Apartamentos, Reforma).
     */
    @Column(length = 100)
    private String buildingType;

    /**
     * Total number of floors in the building, including basement if applicable.
     * Número total de andares na edificação, incluindo subsolo se aplicável.
     */
    private int numberOfFloors;

    /**
     * Indicates if the project includes a basement.
     * Indica se o projeto inclui um subsolo.
     */
    private boolean hasBasement;

    /**
     * The planned budget amount for the project.
     * O valor do orçamento planejado para o projeto.
     */
    @Column(precision = 15, scale = 2) // Use appropriate precision for monetary values
    private BigDecimal budgetAmount;

    /**
     * Currency code for the budget (e.g., CAD, USD, BRL).
     * Código da moeda para o orçamento (ex: CAD, USD, BRL).
     */
    @Column(length = 3)
    private String currency;

    /**
     * Planned start date of the project.
     * Data de início planejada do projeto.
     */
    private LocalDate startDatePlanned;

    /**
     * Planned end date of the project.
     * Data de término planejada do projeto.
     */
    private LocalDate endDatePlanned;

    /**
     * Actual start date of the project.
     * Data de início real do projeto.
     */
    private LocalDate startDateActual;

    /**
     * Actual end date of the project.
     * Data de término real do projeto.
     */
    private LocalDate endDateActual;

    /**
     * Current status of the project.
     * Status atual do projeto.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20) // Ensure length accommodates enum names
    private ProjectStatus status;

    /**
     * Identifier of the client associated with this project (linking to client service).
     * Identificador do cliente associado a este projeto (ligando ao serviço de clientes).
     */
    @Column(name = "client_id") // Store as UUID if client service uses UUIDs
    private UUID clientId;

    /**
     * Identifier of the company branch responsible for the project (linking to company service).
     * Identificador da filial da empresa responsável pelo projeto (ligando ao serviço da empresa).
     */
    @Column(name = "company_branch_id")
    private Long companyBranchId; // Assuming company service uses Long IDs

    // --- Relationships ---

    /**
     * Location details of the project site.
     * Detalhes da localização do local do projeto.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Cascade ALL if location is fully owned by project
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity locationEntity; // Keep the entity relationship

    /**
     * List of owners associated with the project (can be multiple clients or stakeholders).
     * Lista de proprietários associados ao projeto (podem ser múltiplos clientes ou partes interessadas).
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HouseOwner> owners; // Consider renaming HouseOwner if it represents more than just house ownership

    /**
     * List of floors belonging to this project.
     * Lista de andares pertencentes a este projeto.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FloorEntity> floors;

     /**
     * List of references (IDs/URLs) to documents stored in the document-storage-service.
     * Lista de referências (IDs/URLs) para documentos armazenados no document-storage-service.
     */
    @ElementCollection(fetch = FetchType.LAZY) // Use ElementCollection for simple lists of strings
    @CollectionTable(name = "project_document_references", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "document_reference")
    private List<String> documentReferences;
}