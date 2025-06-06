package com.constructionhub.project.repository;

import com.constructionhub.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project Repository
 * 
 * EN: Repository interface for Project entity that provides database operations.
 * Extends JpaRepository to inherit standard CRUD operations and adds custom query methods.
 * 
 * PT: Interface de repositório para a entidade Project que fornece operações de banco de dados.
 * Estende JpaRepository para herdar operações CRUD padrão e adiciona métodos de consulta personalizados.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    
    /**
     * EN: Find a project by its name
     * PT: Encontrar um projeto pelo seu nome
     * 
     * @param name The project name to search for
     * @return An Optional containing the project if found, or empty if not found
     */
    Optional<Project> findByName(String name);
    
    /**
     * EN: Find all projects associated with a specific company
     * PT: Encontrar todos os projetos associados a uma empresa específica
     * 
     * @param companyId The UUID of the company
     * @return A list of projects belonging to the specified company
     */
    List<Project> findByCompanyId(UUID companyId);
    
    /**
     * EN: Find all projects managed by a specific person
     * PT: Encontrar todos os projetos gerenciados por uma pessoa específica
     * 
     * @param managerId The UUID of the manager
     * @return A list of projects managed by the specified person
     */
    List<Project> findByManagerId(UUID managerId);
    
    /**
     * EN: Find all projects with a specific status
     * PT: Encontrar todos os projetos com um status específico
     * 
     * @param status The status to filter by (e.g., "IN_PROGRESS", "COMPLETED")
     * @return A list of projects with the specified status
     */
    List<Project> findByStatus(String status);
}
