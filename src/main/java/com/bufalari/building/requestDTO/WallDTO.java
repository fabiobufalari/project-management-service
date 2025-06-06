package com.bufalari.building.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // Importar

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallDTO {

    @Schema(description = "Unique identifier (UUID) of the wall (for responses or updates)", nullable = true)
    private UUID wallUuid; // <<<--- ADICIONADO para ID da entidade (resposta/update)

    @NotBlank(message = "{wall.wallId.required}")
    @Schema(description = "Textual identifier for the wall (e.g., W1, Parede Norte Sala)", example = "W1-Sala", requiredMode = Schema.RequiredMode.REQUIRED)
    private String wallId;

    @Schema(description = "Optional description for the wall", example = "Parede principal da sala de estar")
    private String description;

    @NotBlank(message = "{wall.type.required}")
    @Schema(description = "Type of wall (e.g., external, internal)", example = "internal", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @NotNull(message = "{wall.lengthFoot.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{wall.lengthFoot.min}")
    @Schema(description = "Length of the wall in feet (integer part)", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private double lengthFoot;

    @Schema(description = "Additional length of the wall in inches (fractional part)", example = "6")
    private double lengthInches;

    @NotNull(message = "{wall.heightFoot.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{wall.heightFoot.min}")
    @Schema(description = "Height of the wall in feet (integer part)", example = "8", requiredMode = Schema.RequiredMode.REQUIRED)
    private double heightFoot;

    @Schema(description = "Additional height of the wall in inches (fractional part)", example = "0")
    private double heightInches;

    @NotNull(message = "{wall.thickness.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{wall.thickness.min}")
    @Schema(description = "Thickness of the wall in inches", example = "5.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private double wallThicknessInch;

    @NotEmpty(message = "{wall.roomSides.required}")
    @Valid
    @Schema(description = "Information about the rooms on each side of the wall", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RoomSideDTO> roomSides; // Mantido requestDTO.RoomSideDTO

    @NotNull(message = "{wall.floorNumber.required}")
    @Schema(description = "Floor number where the wall is located", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int floorNumber;

    @NotNull(message = "{wall.studSpacing.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{wall.studSpacing.min}")
    @Schema(description = "Spacing between studs in inches", example = "16.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double studSpacingInch;

    @Schema(description = "Number of plates (e.g., top, bottom, and possibly double top plate)", example = "3", nullable = true)
    private Integer numberOfPlates;

    @Valid
    @Schema(description = "List of windows in the wall")
    private List<WindowDTO> windows = new ArrayList<>();

    @Valid
    @Schema(description = "List of doors in the wall")
    private List<DoorDTO> doors = new ArrayList<>();

    // Campo para DTO de resposta
    @Schema(description = "Determined material type of the wall (e.g., Concrete, Drywall)", nullable = true, readOnly = true)
    private String materialType;
}