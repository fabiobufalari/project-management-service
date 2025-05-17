package com.bufalari.building.controller;

// import com.bufalari.building.converts.ProjectConverter; // Não usado diretamente aqui
// import com.bufalari.building.entity.ProjectEntity; // Não usado diretamente aqui
import com.bufalari.building.requestDTO.ProjectInfoDTO;
import com.bufalari.building.responseDTO.CalculationResponseDTO;
import com.bufalari.building.responseDTO.ProjectDetailResponseDTO; // <<< Tipo de retorno para create ajustado
import com.bufalari.building.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus; // Não usado diretamente
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
// import java.util.List; // Não usado diretamente
import java.util.UUID;

@RestController
@RequestMapping("/project-management/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "Endpoints for managing construction projects")
public class ProjectController {

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;

    @Operation(summary = "Create a new project", description = "Creates a new construction project record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectDetailResponseDTO.class))), // <<< Retorna ProjectDetailResponseDTO
            @ApiResponse(responseCode = "400", description = "Invalid project data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<ProjectDetailResponseDTO> createProject(@Valid @RequestBody ProjectInfoDTO projectInfoDTO) { // <<< Tipo de corpo da resposta ajustado
        log.info("Request received to create project: {}", projectInfoDTO.getProjectName());
        ProjectDetailResponseDTO createdProjectDTO = projectService.createProject(projectInfoDTO); // <<< projectService.createProject retorna ProjectDetailResponseDTO
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/projects/{id}")
                .buildAndExpand(createdProjectDTO.getProjectId())
                .toUri();
        log.info("Project '{}' created successfully with ID {} at {}", createdProjectDTO.getProjectName(), createdProjectDTO.getProjectId(), location);
        return ResponseEntity.created(location).body(createdProjectDTO);
    }

    @Operation(summary = "Calculate materials for a project", description = "Calculates required materials based on project information. (Note: Full calculation logic might be a stub)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materials calculated successfully",
                         content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CalculationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid project data for calculation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'ENGINEER')")
    public ResponseEntity<CalculationResponseDTO> calculateMaterials(@Valid @RequestBody ProjectInfoDTO projectInfoDTO) {
        log.info("Request received to calculate materials for project: {}", projectInfoDTO.getProjectName());
        CalculationResponseDTO response = projectService.calculateMaterials(projectInfoDTO);
        log.info("Material calculation completed for project: {}", projectInfoDTO.getProjectName());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get project details by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project found",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectDetailResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Project not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()") // Ou uma role mais específica
    public ResponseEntity<ProjectDetailResponseDTO> getProjectById(@PathVariable UUID id) {
        log.debug("Request to get project by ID: {}", id);
        ProjectDetailResponseDTO project = projectService.getProjectDetailById(id);
        return ResponseEntity.ok(project);
    }
}