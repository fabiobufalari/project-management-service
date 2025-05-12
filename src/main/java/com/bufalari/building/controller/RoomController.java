package com.bufalari.building.controller;

import com.bufalari.building.requestDTO.RoomDTO; // Usar DTO para request/response
import com.bufalari.building.requestDTO.WallDTO;   // Usar DTO para request/response
import com.bufalari.building.service.RoomService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "Room Management", description = "Endpoints for managing rooms within projects")
// @SecurityRequirement(name = "bearerAuth") // Se a segurança global não estiver na Application class
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    @Operation(summary = "Create a new room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully",
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RoomDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid room data (e.g., missing project/floor ID, invalid type)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Parent Project or Floor not found")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        log.info("Request to create room: Type '{}', for Floor ID {} and Project ID {}",
                 roomDTO.getRoomType(), roomDTO.getFloorId(), roomDTO.getProjectId());
        RoomDTO savedRoom = roomService.createRoom(roomDTO); // O serviço agora lida com a lógica de associação
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRoom.getId())
                .toUri();
        log.info("Room created successfully with ID {} at {}", savedRoom.getId(), location);
        return ResponseEntity.created(location).body(savedRoom);
    }

    @Operation(summary = "Get room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found",
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RoomDTO.class))),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(value = "/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RoomDTO> getRoomById(
            @Parameter(description = "UUID of the room") @PathVariable UUID roomId) {
        log.debug("Request to get room by ID: {}", roomId);
        RoomDTO roomDTO = roomService.getRoomDtoById(roomId); // Serviço lança ResourceNotFoundException
        return ResponseEntity.ok(roomDTO);
    }

    @Operation(summary = "List all walls for a given room")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Walls listed successfully",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "array", implementation = WallDTO.class))),
        @ApiResponse(responseCode = "404", description = "Room not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(value = "/{roomId}/walls", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WallDTO>> getWallsForRoom(
            @Parameter(description = "UUID of the room") @PathVariable UUID roomId) {
        log.debug("Request to get walls for room ID: {}", roomId);
        List<WallDTO> walls = roomService.getWallsForRoom(roomId); // Método a ser implementado/ajustado no RoomService
        return ResponseEntity.ok(walls);
    }

    @Operation(summary = "Update an existing room")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Room updated successfully",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RoomDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid room data"),
        @ApiResponse(responseCode = "404", description = "Room, Project, or Floor not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping(value = "/{roomId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<RoomDTO> updateRoom(
            @Parameter(description = "UUID of the room to update") @PathVariable UUID roomId,
            @Valid @RequestBody RoomDTO roomDTO) {
        log.info("Request to update room ID: {}", roomId);
        if (roomDTO.getId() != null && !roomDTO.getId().equals(roomId)) {
            log.warn("Path ID {} does not match body ID {}. Using path ID for update.", roomId, roomDTO.getId());
        }
        // O DTO para update pode não precisar de projectId e floorId se eles não puderem ser alterados,
        // ou o serviço pode ignorá-los no update e usar o ID do path para buscar a entidade.
        RoomDTO updatedRoom = roomService.updateRoom(roomId, roomDTO); // Método a ser implementado no RoomService
        return ResponseEntity.ok(updatedRoom);
    }

    @Operation(summary = "Delete a room by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Room not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "409", description = "Conflict (e.g., room still has walls or other dependencies)")
    })
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(
            @Parameter(description = "UUID of the room to delete") @PathVariable UUID roomId) {
        log.info("Request to delete room ID: {}", roomId);
        roomService.deleteRoom(roomId); // Método a ser implementado no RoomService
        return ResponseEntity.noContent().build();
    }

    // O endpoint original "/{roomId}/walls" para POST (adicionar parede) foi comentado
    // porque a adição de paredes geralmente é parte da definição do projeto/andar/cômodo,
    // e não uma operação isolada sobre um cômodo já existente, a menos que seja uma edição
    // muito granular. Se essa funcionalidade for necessária, o RoomService precisaria de um
    // método como `addWallToRoom(UUID roomId, WallDTO wallDetails)` que criaria
    // a WallEntity, a WallRoomMappingEntity e as associaria.
}