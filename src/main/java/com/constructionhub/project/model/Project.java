package com.constructionhub.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project Entity
 * 
 * EN: Represents a construction project in the system.
 * This entity stores all relevant information about a project including timeline,
 * budget, location, and completion status.
 * 
 * PT: Representa um projeto de construção no sistema.
 * Esta entidade armazena todas as informações relevantes sobre um projeto, incluindo
 * cronograma, orçamento, localização e status de conclusão.
 */
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    /**
     * EN: Unique identifier for the project
     * PT: Identificador único para o projeto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * EN: Name of the project
     * PT: Nome do projeto
     */
    @Column(nullable = false)
    private String name;

    /**
     * EN: Detailed description of the project
     * PT: Descrição detalhada do projeto
     */
    @Column
    private String description;

    /**
     * EN: Date when the project starts
     * PT: Data de início do projeto
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * EN: Actual end date of the project (may be null if project is ongoing)
     * PT: Data de término real do projeto (pode ser nulo se o projeto estiver em andamento)
     */
    @Column
    private LocalDate endDate;

    /**
     * EN: Estimated completion date for the project
     * PT: Data estimada de conclusão do projeto
     */
    @Column
    private LocalDate estimatedEndDate;

    /**
     * EN: Total budget allocated for the project
     * PT: Orçamento total alocado para o projeto
     */
    @Column
    private BigDecimal budget;

    /**
     * EN: Current accumulated cost of the project
     * PT: Custo acumulado atual do projeto
     */
    @Column
    private BigDecimal costToDate;

    /**
     * EN: Reference to the company responsible for the project
     * PT: Referência à empresa responsável pelo projeto
     */
    @Column(name = "company_id")
    private UUID companyId;

    /**
     * EN: Reference to the project manager
     * PT: Referência ao gerente do projeto
     */
    @Column(name = "manager_id")
    private UUID managerId;

    /**
     * EN: Physical location of the project
     * PT: Localização física do projeto
     */
    @Column
    private String location;

    /**
     * EN: Current status of the project (e.g., PLANNING, IN_PROGRESS, COMPLETED)
     * PT: Status atual do projeto (ex: PLANEJAMENTO, EM_ANDAMENTO, CONCLUÍDO)
     */
    @Column
    private String status;

    /**
     * EN: Percentage of project completion (0-100)
     * PT: Percentual de conclusão do projeto (0-100)
     */
    @Column
    private Integer completionPercentage;

    /**
     * EN: Flag indicating if the project is active
     * PT: Indicador se o projeto está ativo
     */
    @Column(name = "is_active")
    private boolean active = true;

    /**
     * EN: Timestamp when the project record was created
     * PT: Timestamp de quando o registro do projeto foi criado
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * EN: Timestamp when the project record was last updated
     * PT: Timestamp de quando o registro do projeto foi atualizado pela última vez
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
