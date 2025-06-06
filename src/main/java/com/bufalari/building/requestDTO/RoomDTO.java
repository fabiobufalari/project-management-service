package com.bufalari.building.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.util.List; // Descomente se for incluir List<WallDTO> walls;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    @Schema(description = "Unique identifier (UUID) of the room", example = "room_uuid_example", readOnly = true)
    private UUID id;

    @NotBlank(message = "{room.type.required}")
    @Size(max = 100, message = "{room.type.size}")
    @Schema(description = "Type or name of the room (e.g., Living Room, Master Bedroom)", example = "Living Room", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roomType;

    @Schema(description = "Indicates if the room is a wet area (e.g., bathroom, kitchen)", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean isWetArea;

    @NotNull(message = "{room.projectId.required}")
    @Schema(description = "UUID of the project this room belongs to", example = "project_uuid_example", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID projectId;

    @NotNull(message = "{room.floorId.required}")
    @Schema(description = "UUID of the floor this room belongs to", example = "floor_uuid_example", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID floorId;

    @Schema(description = "Name of the project this room belongs to (populated by service)", example = "Residencial Alpha", readOnly = true)
    private String projectName;

    @Schema(description = "Number of the floor this room belongs to (populated by service)", example = "1", readOnly = true)
    private Integer floorNumberInfo;

    // Opcional:
    // @Valid
    // @Schema(description = "List of walls associated with this room (optional in request, populated in response)")
    // private List<WallDTO> walls;
}