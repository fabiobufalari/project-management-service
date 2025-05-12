package com.bufalari.building.responseDTO;

import com.bufalari.building.enums.ProjectStatus;
import com.bufalari.building.requestDTO.LocationDTO; // LocationDTO é usado como está para request e response simples
// Import CORRETO para FloorResponseDTO (do pacote responseDTO)
import com.bufalari.building.responseDTO.FloorResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponseDTO {

    @Schema(description = "Unique identifier (UUID) of the project")
    private UUID projectId;

    @Schema(description = "Legacy Long ID of the project (if applicable)", nullable = true)
    private Long projectIdLegacy;

    @Schema(description = "Name of the construction project")
    private String projectName;

    @Schema(description = "Date and time of project registration or last update (ISO 8601 format)")
    private String dateTime;

    @Schema(description = "Location details of the project")
    private LocationDTO location; // Mantido requestDTO.LocationDTO, pois é simples e usado na entrada também

    @Schema(description = "Type of building")
    private String buildingType;

    @Schema(description = "Total number of floors in the building")
    private int numberOfFloors;

    @Schema(description = "Indicates if the project includes a basement")
    private boolean hasBasement;

    @Schema(description = "Current status of the project")
    private ProjectStatus status;

    @Schema(description = "Planned budget amount for the project", nullable = true)
    private BigDecimal budgetAmount;

    @Schema(description = "Currency for the budget", nullable = true)
    private String currency;

    @Schema(description = "Planned start date of the project", nullable = true)
    private LocalDate startDatePlanned;

    @Schema(description = "Planned end date of the project", nullable = true)
    private LocalDate endDatePlanned;

    @Schema(description = "Actual start date of the project", nullable = true)
    private LocalDate startDateActual;

    @Schema(description = "Actual end date of the project", nullable = true)
    private LocalDate endDateActual;

    @Schema(description = "UUID of the client associated with this project", nullable = true)
    private UUID clientId;

    @Schema(description = "ID of the company branch (assuming Long ID)", example = "1", nullable = true)
    private Long companyBranchId;

    @Schema(description = "List of floors in the project")
    // AQUI ESTAVA O ERRO ANTERIORMENTE, AGORA CORRIGIDO:
    private List<com.bufalari.building.responseDTO.FloorResponseDTO> floors;

    @Schema(description = "User who created the record", readOnly = true)
    private String createdBy;

    @Schema(description = "Timestamp of record creation", readOnly = true)
    private LocalDateTime createdAt;

    @Schema(description = "User who last modified the record", readOnly = true)
    private String lastModifiedBy;

    @Schema(description = "Timestamp of last modification", readOnly = true)
    private LocalDateTime lastModifiedAt;
}