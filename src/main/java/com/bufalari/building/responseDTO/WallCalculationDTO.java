package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WallCalculationDTO {

    @Schema(description = "Unique identifier (UUID) of the Wall entity this calculation refers to")
    private UUID wallUuid; // <<<--- Adicionado UUID da entidade Wall

    @Schema(description = "Textual identifier of the wall (e.g., A1, B2)", example = "A1")
    private String wallIdentification; // O ID textual (ex: "Parede A1")

    @Schema(description = "Description of the wall", example = "Living Room North Wall")
    private String description;

    @Schema(description = "Side of the wall (e.g., External, Internal-LivingRoom)", example = "External")
    private String sideOfWall; // External ou Internal (ou lado específico do cômodo)

    @Schema(description = "Total length of the wall in feet", example = "20.5")
    private double totalWallLengthFoot;

    @Schema(description = "Thickness of the wall in inches", example = "5.5")
    private double wallThicknessInch;

    @Schema(description = "Indicates if the wall is adjacent to a wet area (e.g., bathroom, kitchen)", example = "false")
    private boolean isWetArea; // Informação vinda de RoomEntity

    @Schema(description = "Number of top/bottom plates for framing", example = "3")
    private int numberOfPlates;

    @Schema(description = "Number of headers (lintels) required for openings", example = "2")
    private int numberOfHeaders; // Pode ser calculado baseado em portas/janelas

    @Schema(description = "Number of plywood sheets (or other sheathing)", example = "10")
    private int numberOfPlywoodSheets; // Para paredes externas ou específicas

    @Schema(description = "Number of drywall sheets", example = "20")
    private int numberOfDrywallSheets;

    @Schema(description = "Type of material for the wall (e.g., Concrete, Drywall, Moisture Resistant Drywall)", example = "Drywall")
    private String materialType; // Determinado pela lógica (externa, área molhada)

    @Schema(description = "List of stud calculations for this wall")
    private List<StudDTO> studs;

    @Schema(description = "List of window calculations for this wall")
    private List<WindowCalculationDTO> windows; // WindowCalculationDTO também usará UUID

    @Schema(description = "List of door calculations for this wall")
    private List<DoorCalculationDTO> doors; // DoorCalculationDTO também usará UUID

    @Schema(description = "Code reference for wall assembly or primary material", example = "WALL-STD-DRY-2X4")
    private String codeReference;
}