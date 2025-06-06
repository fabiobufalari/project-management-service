package com.constructionhub.project.service;

import com.constructionhub.project.dto.ProjectDTO;
import com.constructionhub.project.exception.ResourceAlreadyExistsException;
import com.constructionhub.project.exception.ResourceNotFoundException;
import com.constructionhub.project.model.Project;
import com.constructionhub.project.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Project Service
 * 
 * EN: Service class that handles business logic for project operations.
 * This service provides methods for creating, retrieving, updating, and deleting projects,
 * as well as specialized operations for project management.
 * 
 * PT: Classe de serviço que lida com a lógica de negócios para operações de projetos.
 * Este serviço fornece métodos para criar, recuperar, atualizar e excluir projetos,
 * bem como operações especializadas para gerenciamento de projetos.
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * EN: Constructor with dependency injection
     * PT: Construtor com injeção de dependência
     * 
     * @param projectRepository EN: The project repository / PT: O repositório de projetos
     */
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Create a new project
     * 
     * EN: Creates a new project after validating that no project with the same name exists.
     * 
     * PT: Cria um novo projeto após validar que nenhum projeto com o mesmo nome existe.
     * 
     * @param projectDTO EN: The project data transfer object / PT: O objeto de transferência de dados do projeto
     * @return EN: The created project / PT: O projeto criado
     * @throws ResourceAlreadyExistsException EN: If a project with the same name already exists / PT: Se um projeto com o mesmo nome já existir
     */
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        projectRepository.findByName(projectDTO.getName())
                .ifPresent(project -> {
                    throw new ResourceAlreadyExistsException("Project", "name", projectDTO.getName());
                });

        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        Project savedProject = projectRepository.save(project);
        
        ProjectDTO savedProjectDTO = new ProjectDTO();
        BeanUtils.copyProperties(savedProject, savedProjectDTO);
        return savedProjectDTO;
    }

    /**
     * Get all projects
     * 
     * EN: Retrieves all projects in the system.
     * 
     * PT: Recupera todos os projetos no sistema.
     * 
     * @return EN: List of all projects / PT: Lista de todos os projetos
     */
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    BeanUtils.copyProperties(project, projectDTO);
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get project by ID
     * 
     * EN: Retrieves a specific project by its unique identifier.
     * 
     * PT: Recupera um projeto específico pelo seu identificador único.
     * 
     * @param id EN: The project ID / PT: O ID do projeto
     * @return EN: The requested project / PT: O projeto solicitado
     * @throws ResourceNotFoundException EN: If the project is not found / PT: Se o projeto não for encontrado
     */
    public ProjectDTO getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }

    /**
     * Update project
     * 
     * EN: Updates an existing project with new information.
     * 
     * PT: Atualiza um projeto existente com novas informações.
     * 
     * @param id EN: The ID of the project to update / PT: O ID do projeto a ser atualizado
     * @param projectDTO EN: The updated project data / PT: Os dados atualizados do projeto
     * @return EN: The updated project / PT: O projeto atualizado
     * @throws ResourceNotFoundException EN: If the project is not found / PT: Se o projeto não for encontrado
     */
    public ProjectDTO updateProject(UUID id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        // Check if name is being changed and if new name already exists
        if (!project.getName().equals(projectDTO.getName())) {
            projectRepository.findByName(projectDTO.getName())
                    .ifPresent(existingProject -> {
                        if (!existingProject.getId().equals(id)) {
                            throw new ResourceAlreadyExistsException("Project", "name", projectDTO.getName());
                        }
                    });
        }
        
        BeanUtils.copyProperties(projectDTO, project, "id", "createdAt");
        Project updatedProject = projectRepository.save(project);
        
        ProjectDTO updatedProjectDTO = new ProjectDTO();
        BeanUtils.copyProperties(updatedProject, updatedProjectDTO);
        return updatedProjectDTO;
    }

    /**
     * Delete project
     * 
     * EN: Deletes a project from the system.
     * 
     * PT: Exclui um projeto do sistema.
     * 
     * @param id EN: The ID of the project to delete / PT: O ID do projeto a ser excluído
     * @throws ResourceNotFoundException EN: If the project is not found / PT: Se o projeto não for encontrado
     */
    public void deleteProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        
        projectRepository.delete(project);
    }

    /**
     * Get projects by company
     * 
     * EN: Retrieves all projects associated with a specific company.
     * 
     * PT: Recupera todos os projetos associados a uma empresa específica.
     * 
     * @param companyId EN: The company ID / PT: O ID da empresa
     * @return EN: List of projects for the company / PT: Lista de projetos da empresa
     */
    public List<ProjectDTO> getProjectsByCompany(UUID companyId) {
        List<Project> projects = projectRepository.findByCompanyId(companyId);
        return projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    BeanUtils.copyProperties(project, projectDTO);
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get projects by manager
     * 
     * EN: Retrieves all projects managed by a specific person.
     * 
     * PT: Recupera todos os projetos gerenciados por uma pessoa específica.
     * 
     * @param managerId EN: The manager ID / PT: O ID do gerente
     * @return EN: List of projects for the manager / PT: Lista de projetos do gerente
     */
    public List<ProjectDTO> getProjectsByManager(UUID managerId) {
        List<Project> projects = projectRepository.findByManagerId(managerId);
        return projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    BeanUtils.copyProperties(project, projectDTO);
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get projects by status
     * 
     * EN: Retrieves all projects with a specific status.
     * 
     * PT: Recupera todos os projetos com um status específico.
     * 
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of projects with the specified status / PT: Lista de projetos com o status especificado
     */
    public List<ProjectDTO> getProjectsByStatus(String status) {
        List<Project> projects = projectRepository.findByStatus(status);
        return projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    BeanUtils.copyProperties(project, projectDTO);
                    return projectDTO;
                })
                .collect(Collectors.toList());
    }
}
