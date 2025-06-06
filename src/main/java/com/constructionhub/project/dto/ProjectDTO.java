package com.constructionhub.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Project Data Transfer Object
 * 
 * EN: This DTO is used for transferring project data between the service layer and controllers.
 * It includes validation constraints and represents the project entity in API operations.
 * 
 * PT: Este DTO é usado para transferir dados de projetos entre a camada de serviço e os controladores.
 * Inclui restrições de validação e representa a entidade de projeto nas operações da API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    /**
     * EN: Unique identifier for the project
     * PT: Identificador único para o projeto
     */
    private UUID id;
    
    /**
     * EN: Name of the project (required field)
     * PT: Nome do projeto (campo obrigatório)
     */
    @NotBlank(message = "Nome do projeto é obrigatório")
    private String name;
    
    /**
     * EN: Detailed description of the project
     * PT: Descrição detalhada do projeto
     */
    private String description;
    
    /**
     * EN: Date when the project starts (required field)
     * PT: Data de início do projeto (campo obrigatório)
     */
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate startDate;
    
    /**
     * EN: Actual end date of the project
     * PT: Data de término real do projeto
     */
    private LocalDate endDate;
    
    /**
     * EN: Estimated completion date for the project
     * PT: Data estimada de conclusão do projeto
     */
    private LocalDate estimatedEndDate;
    
    /**
     * EN: Total budget allocated for the project
     * PT: Orçamento total alocado para o projeto
     */
    private BigDecimal budget;
    
    /**
     * EN: Current accumulated cost of the project
     * PT: Custo acumulado atual do projeto
     */
    private BigDecimal costToDate;
    
    /**
     * EN: Reference to the company responsible for the project
     * PT: Referência à empresa responsável pelo projeto
     */
    private UUID companyId;
    
    /**
     * EN: Reference to the project manager
     * PT: Referência ao gerente do projeto
     */
    private UUID managerId;
    
    /**
     * EN: Physical location of the project
     * PT: Localização física do projeto
     */
    private String location;
    
    /**
     * EN: Current status of the project
     * PT: Status atual do projeto
     */
    private String status;
    
    /**
     * EN: Percentage of project completion (0-100)
     * PT: Percentual de conclusão do projeto (0-100)
     */
    private Integer completionPercentage;
    
    /**
     * EN: Flag indicating if the project is active
     * PT: Indicador se o projeto está ativo
     */
    private boolean active;
}
