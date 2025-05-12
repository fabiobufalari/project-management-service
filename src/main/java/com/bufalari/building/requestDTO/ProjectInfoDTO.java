package com.bufalari.building.requestDTO;

import com.bufalari.building.enums.ProjectStatus; // Importar
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // Importar
import java.time.LocalDate; // Importar
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDTO {

    @Schema(description = "Unique identifier (UUID) of the project (used for updates, null for creation)",
            example = "project_uuid_123", nullable = true)
    private UUID projectId; // ID do projeto (UUID), pode ser nulo na criação

    @Schema(description = "Legacy Long ID of the project (if applicable)", example = "101", nullable = true)
    private Long projectIdLegacy; // ID legado, se existir

    @NotBlank(message = "{project.name.required}")
    @Schema(description = "Name of the construction project", example = "Residencial Flores", requiredMode = Schema.RequiredMode.REQUIRED)
    private String projectName;

    @Schema(description = "Date and time of project registration (ISO 8601 format)", example = "2024-05-05T10:15:30", nullable = true)
    private String dateTime;

    @NotNull(message = "{project.location.required}")
    @Valid
    @Schema(description = "Location details of the project", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocationDTO location;

    @NotBlank(message = "{project.buildingType.required}")
    @Schema(description = "Type of building", example = "Residential House", requiredMode = Schema.RequiredMode.REQUIRED)
    private String buildingType;

    @NotNull(message = "{project.numberOfFloors.required}")
    @Min(value = 0, message = "{project.numberOfFloors.min}") // 0 para térreo/sem andares definidos
    @Schema(description = "Total number of floors", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int numberOfFloors;

    @Schema(description = "Indicates if the project includes a basement", example = "false")
    private boolean hasBasement;

    @Schema(description = "Current status of the project", example = "PLANNING", nullable = true)
    private ProjectStatus status;

    @Schema(description = "Planned budget amount for the project", example = "500000.00", nullable = true)
    private BigDecimal budgetAmount;

    @Schema(description = "Currency for the budget (e.g., CAD, USD, BRL)", example = "BRL", nullable = true)
    private String currency;

    @Schema(description = "Planned start date of the project", example = "2025-01-15", nullable = true)
    private LocalDate startDatePlanned;

    @Schema(description = "Planned end date of the project", example = "2025-12-31", nullable = true)
    private LocalDate endDatePlanned;

    @Schema(description = "UUID of the client associated with this project", nullable = true)
    private UUID clientId; // <<<--- UUID para referência ao cliente

    @Schema(description = "ID of the company branch (assuming Long ID)", example = "1", nullable = true)
    private Long companyBranchId; // <<<--- Mantido como Long

    @NotEmpty(message = "{project.calculationStructure.required}")
    @Valid
    @Schema(description = "List of floor structures and their components", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CalculationStructureDTO> calculationStructure;
}