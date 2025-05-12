package com.bufalari.building.entity;

import com.bufalari.building.auditing.AuditableBaseEntity;
import com.bufalari.building.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Table(name = "projects", indexes = {
    @Index(name = "idx_project_name", columnList = "projectName"),
    @Index(name = "idx_project_client_id", columnList = "client_id"),
    @Index(name = "idx_project_status", columnList = "status")
})
public class ProjectEntity extends AuditableBaseEntity {

    @Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
	private UUID id; // <<<--- UUID

    @Column(name = "project_id_legacy") // Mantém o ID Long legado, se necessário
    private Long projectIdLegacy;

    @Column(nullable = false, length = 200)
    private String projectName;

    private LocalDateTime dateTime; // Data de registro/criação do projeto

    @Column(length = 100)
    private String buildingType;

    private int numberOfFloors;
    private boolean hasBasement;

    @Column(precision = 15, scale = 2)
    private BigDecimal budgetAmount;

    @Column(length = 3)
    private String currency;

    private LocalDate startDatePlanned;
    private LocalDate endDatePlanned;
    private LocalDate startDateActual;
    private LocalDate endDateActual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status;

    @Column(name = "client_id", columnDefinition = "uuid") // Cliente associado
    private UUID clientId; // <<<--- UUID (assumindo que ClientService usa UUID)

    @Column(name = "company_branch_id") // Filial da empresa
    private Long companyBranchId; // Mantém Long (ou UUID se CompanyService usar UUID)

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_project_location"))
    private LocationEntity locationEntity;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<HouseOwner> owners = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<FloorEntity> floors = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_document_references",
                     joinColumns = @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "fk_projdoc_project")))
    @Column(name = "document_reference", length = 500) // Aumentar tamanho se URLs longas
    @Builder.Default
    private List<String> documentReferences = new ArrayList<>();

    // --- Métodos auxiliares para gerenciar relações bidirecionais ---
    public void addFloor(FloorEntity floor) {
        if (floor != null) {
            if (this.floors == null) this.floors = new ArrayList<>();
            this.floors.add(floor);
            floor.setProject(this);
        }
    }
    public void removeFloor(FloorEntity floor) {
        if (floor != null && this.floors != null) {
            this.floors.remove(floor);
            floor.setProject(null);
        }
    }
    public void addOwner(HouseOwner owner) {
        if (owner != null) {
            if (this.owners == null) this.owners = new ArrayList<>();
            this.owners.add(owner);
            owner.setProject(this);
        }
    }
    // ... (outros métodos auxiliares conforme necessário)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ProjectEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : getClass().hashCode();
    }
}