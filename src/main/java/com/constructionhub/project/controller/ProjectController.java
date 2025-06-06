package com.constructionhub.project.controller;

import com.constructionhub.project.dto.ProjectDTO;
import com.constructionhub.project.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Project Controller
 * 
 * EN: REST controller that handles HTTP requests related to projects.
 * Provides endpoints for CRUD operations and specialized project management functions.
 * 
 * PT: Controlador REST que lida com requisições HTTP relacionadas a projetos.
 * Fornece endpoints para operações CRUD e funções especializadas de gerenciamento de projetos.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * EN: Constructor with dependency injection
     * PT: Construtor com injeção de dependência
     * 
     * @param projectService EN: The project service / PT: O serviço de projetos
     */
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Create a new project
     * 
     * EN: Endpoint to create a new project in the system.
     * 
     * PT: Endpoint para criar um novo projeto no sistema.
     * 
     * @param projectDTO EN: The project data / PT: Os dados do projeto
     * @return EN: The created project with HTTP 201 Created status / PT: O projeto criado com status HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO createdProject = projectService.createProject(projectDTO);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    /**
     * Get all projects
     * 
     * EN: Endpoint to retrieve all projects in the system.
     * 
     * PT: Endpoint para recuperar todos os projetos no sistema.
     * 
     * @return EN: List of all projects / PT: Lista de todos os projetos
     */
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    /**
     * Get project by ID
     * 
     * EN: Endpoint to retrieve a specific project by its ID.
     * 
     * PT: Endpoint para recuperar um projeto específico pelo seu ID.
     * 
     * @param id EN: The project ID / PT: O ID do projeto
     * @return EN: The requested project / PT: O projeto solicitado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable UUID id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Update project
     * 
     * EN: Endpoint to update an existing project.
     * 
     * PT: Endpoint para atualizar um projeto existente.
     * 
     * @param id EN: The ID of the project to update / PT: O ID do projeto a ser atualizado
     * @param projectDTO EN: The updated project data / PT: Os dados atualizados do projeto
     * @return EN: The updated project / PT: O projeto atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable UUID id, @Valid @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    /**
     * Delete project
     * 
     * EN: Endpoint to delete a project from the system.
     * 
     * PT: Endpoint para excluir um projeto do sistema.
     * 
     * @param id EN: The ID of the project to delete / PT: O ID do projeto a ser excluído
     * @return EN: HTTP 204 No Content status / PT: Status HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get projects by company
     * 
     * EN: Endpoint to retrieve all projects for a specific company.
     * 
     * PT: Endpoint para recuperar todos os projetos de uma empresa específica.
     * 
     * @param companyId EN: The company ID / PT: O ID da empresa
     * @return EN: List of projects for the company / PT: Lista de projetos da empresa
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByCompany(@PathVariable UUID companyId) {
        List<ProjectDTO> projects = projectService.getProjectsByCompany(companyId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get projects by manager
     * 
     * EN: Endpoint to retrieve all projects managed by a specific person.
     * 
     * PT: Endpoint para recuperar todos os projetos gerenciados por uma pessoa específica.
     * 
     * @param managerId EN: The manager ID / PT: O ID do gerente
     * @return EN: List of projects for the manager / PT: Lista de projetos do gerente
     */
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByManager(@PathVariable UUID managerId) {
        List<ProjectDTO> projects = projectService.getProjectsByManager(managerId);
        return ResponseEntity.ok(projects);
    }

    /**
     * Get projects by status
     * 
     * EN: Endpoint to retrieve all projects with a specific status.
     * 
     * PT: Endpoint para recuperar todos os projetos com um status específico.
     * 
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of projects with the specified status / PT: Lista de projetos com o status especificado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByStatus(@PathVariable String status) {
        List<ProjectDTO> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }
}
