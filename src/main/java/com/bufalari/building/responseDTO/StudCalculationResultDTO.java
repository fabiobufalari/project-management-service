package com.bufalari.building.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // Importar para wallUuid (opcional)

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudCalculationResultDTO {

    // Se wallId se refere ao ID textual/de negócio da parede (ex: "Parede A1")
    @Schema(description = "Textual identifier of the wall", example = "A1")
    private String wallId;

    // Opcional: Se você também quer retornar o UUID da WallEntity
    @Schema(description = "Unique identifier (UUID) of the Wall entity")
    private UUID wallUuid; // <<<--- Adicionado UUID da entidade parede

    @Schema(description = "Total number of studs required for this wall", example = "55")
    private int studCount;

    @Schema(description = "Total linear footage of stud material required for this wall", example = "440.0")
    private double studLinearFootage;
}