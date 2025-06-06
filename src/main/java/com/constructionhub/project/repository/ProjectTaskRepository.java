package com.constructionhub.project.repository;

import com.constructionhub.project.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Project Task Repository
 * 
 * EN: Repository interface for ProjectTask entity that provides database operations.
 * Extends JpaRepository to inherit standard CRUD operations and adds custom query methods
 * for task-specific operations.
 * 
 * PT: Interface de repositório para a entidade ProjectTask que fornece operações de banco de dados.
 * Estende JpaRepository para herdar operações CRUD padrão e adiciona métodos de consulta personalizados
 * para operações específicas de tarefas.
 */
@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
    
    /**
     * EN: Find all tasks associated with a specific project
     * PT: Encontrar todas as tarefas associadas a um projeto específico
     * 
     * @param projectId The UUID of the project
     * @return A list of tasks belonging to the specified project
     */
    List<ProjectTask> findByProjectId(UUID projectId);
    
    /**
     * EN: Find all tasks assigned to a specific person
     * PT: Encontrar todas as tarefas atribuídas a uma pessoa específica
     * 
     * @param assignedToId The UUID of the person assigned to the tasks
     * @return A list of tasks assigned to the specified person
     */
    List<ProjectTask> findByAssignedToId(UUID assignedToId);
    
    /**
     * EN: Find all tasks with a specific status
     * PT: Encontrar todas as tarefas com um status específico
     * 
     * @param status The status to filter by (e.g., "TODO", "IN_PROGRESS", "COMPLETED")
     * @return A list of tasks with the specified status
     */
    List<ProjectTask> findByStatus(String status);
    
    /**
     * EN: Find all tasks for a specific project with a specific status
     * PT: Encontrar todas as tarefas de um projeto específico com um status específico
     * 
     * @param projectId The UUID of the project
     * @param status The status to filter by
     * @return A list of tasks belonging to the specified project with the specified status
     */
    List<ProjectTask> findByProjectIdAndStatus(UUID projectId, String status);
}
