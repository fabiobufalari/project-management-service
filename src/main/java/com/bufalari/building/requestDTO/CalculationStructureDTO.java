package com.bufalari.building.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList; // Importar
import java.util.List;
import java.util.UUID;    // Importar

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationStructureDTO { // Representa um Andar para entrada

    @Schema(description = "Unique identifier (UUID) of the floor (if updating, null for creation)", nullable = true)
    private UUID floorUuid; // <<<--- ID UUID do andar (opcional na entrada, usado para update)

    @Schema(description = "Legacy Long ID of the floor (if applicable)", nullable = true)
    private Long floorId; // <<<--- ID Long legado (opcional)

    @NotNull(message = "{floor.number.required}")
    @Schema(description = "Floor number (e.g., 0 for ground, 1 for first)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int floorNumber;

    @NotNull(message = "{floor.area.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{floor.area.min}")
    @Schema(description = "Total area of the floor in square feet", example = "1500.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private double areaSquareFeet;

    @Schema(description = "Indicates if the floor is heated", example = "true")
    private boolean heated;

    @Schema(description = "Primary material of the floor structure/finish", example = "Concrete Slab", nullable = true)
    private String material;

    // 'isWetArea' é mais apropriado para RoomDTO, não para o andar inteiro geralmente.
    // private boolean isWetArea;

    @Valid // Validar cada WallDTO na lista
    @Schema(description = "List of walls for this floor")
    private List<WallDTO> walls = new ArrayList<>();

    @Valid @Schema(description = "Ceiling details for this floor", nullable = true)
    private CeilingDTO ceiling;
    @Valid @Schema(description = "Baseboard details for this floor", nullable = true)
    private BaseboardDTO baseboards;
    @Valid @Schema(description = "Painting details for this floor", nullable = true)
    private PaintingDTO painting;
    @Valid @Schema(description = "Balcony details for this floor (if any)", nullable = true)
    private BalconyDTO balcony;
    @Valid @Schema(description = "Bathroom accessories for this floor (if any)", nullable = true)
    private BathroomAccessoriesDTO bathroomAccessories;
    @Valid @Schema(description = "Kitchen accessories for this floor (if any)", nullable = true)
    private KitchenAccessoriesDTO kitchenAccessories;
    @Valid @Schema(description = "Roof details (usually only for the top floor or single-story)", nullable = true)
    private RoofDTO roof;
}