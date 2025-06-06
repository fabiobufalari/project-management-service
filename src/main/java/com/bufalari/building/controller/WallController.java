package com.bufalari.building.controller;

import com.bufalari.building.requestDTO.WallDTO; // Usar DTO para request
import com.bufalari.building.responseDTO.WallCalculationDTO; // Usar DTO para response
import com.bufalari.building.service.WallCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project-management/walls")
@RequiredArgsConstructor
@Tag(name = "Wall Management & Calculation", description = "Endpoints for managing walls and calculating materials")
public class WallController {

    private static final Logger log = LoggerFactory.getLogger(WallController.class);
    private final WallCalculationService wallCalculationService;

    @Operation(summary = "Calculate wall materials (and optionally persist)",
               description = "Calculates materials for a list of walls. Optionally persists the wall data if projectId and floorId are provided.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Wall calculations successful (and persisted if IDs provided)",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "array", implementation = WallCalculationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid wall data provided or project/floor mismatch"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Project or Floor not found if IDs are provided for persistence")
    })
    @PostMapping(value = "/calculate-or-create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // Endpoint renomeado para clareza
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'ENGINEER')")
    public ResponseEntity<List<WallCalculationDTO>> calculateAndOptionalyCreateWalls( // Nome do método mantido para consistência com print
            @Valid @RequestBody List<WallDTO> wallDTOs,
            @Parameter(description = "ID of the project this wall belongs to (required if persisting)") @RequestParam(required = false) UUID projectId,
            @Parameter(description = "ID of the floor this wall belongs to (required if persisting)") @RequestParam(required = false) UUID floorId
            ) {
        log.info("Request to calculate and {} walls for project ID {} / floor ID {}. Number of walls: {}",
                (projectId != null && floorId != null ? "create" : "preview"), projectId, floorId, wallDTOs.size());

        // Chamada corrigida para o método em WallCalculationService
        List<WallCalculationDTO> calculatedWalls = wallCalculationService.processWallDTOs(wallDTOs, projectId, floorId);

        // Se criou (IDs fornecidos), o status poderia ser 201 Created, mas precisaria retornar URIs, o que complica para uma lista.
        // Para simplificar, retornando 200 OK. A resposta contém os DTOs com os IDs UUID se foram persistidos.
        return ResponseEntity.ok(calculatedWalls);
    }


    @Operation(summary = "Get a specific wall by its UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Wall found",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WallDTO.class))),
        @ApiResponse(responseCode = "404", description = "Wall not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(value = "/{wallUuid}", produces = MediaType.APPLICATION_JSON_VALUE) // Path param renomeado para clareza
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<WallDTO> getWallByUuid(
            @Parameter(description = "UUID of the wall") @PathVariable UUID wallUuid) {
        log.debug("Request to get wall by UUID: {}", wallUuid);
        WallDTO wallDTO = wallCalculationService.getWallDtoByUuid(wallUuid);
        return ResponseEntity.ok(wallDTO);
    }

    // TODO: Adicionar PUT para atualizar Wall (ex: dimensões, tipo, mas não recálculo direto aqui)
    // TODO: Adicionar DELETE para remover Wall (se permitido e não calculado dinamicamente)
}