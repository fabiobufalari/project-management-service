package com.constructionhub.project.controller;

import com.constructionhub.project.dto.ProjectTaskDTO;
import com.constructionhub.project.service.ProjectTaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Project Task Controller
 * 
 * EN: REST controller that handles HTTP requests related to project tasks.
 * Provides endpoints for CRUD operations and specialized task management functions.
 * 
 * PT: Controlador REST que lida com requisições HTTP relacionadas a tarefas de projetos.
 * Fornece endpoints para operações CRUD e funções especializadas de gerenciamento de tarefas.
 */
@RestController
@RequestMapping("/api/tasks")
public class ProjectTaskController {

    private final ProjectTaskService taskService;

    /**
     * EN: Constructor with dependency injection
     * PT: Construtor com injeção de dependência
     * 
     * @param taskService EN: The project task service / PT: O serviço de tarefas de projeto
     */
    @Autowired
    public ProjectTaskController(ProjectTaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Create a new task
     * 
     * EN: Endpoint to create a new task in the system.
     * 
     * PT: Endpoint para criar uma nova tarefa no sistema.
     * 
     * @param taskDTO EN: The task data / PT: Os dados da tarefa
     * @return EN: The created task with HTTP 201 Created status / PT: A tarefa criada com status HTTP 201 Created
     */
    @PostMapping
    public ResponseEntity<ProjectTaskDTO> createTask(@Valid @RequestBody ProjectTaskDTO taskDTO) {
        ProjectTaskDTO createdTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Get all tasks
     * 
     * EN: Endpoint to retrieve all tasks in the system.
     * 
     * PT: Endpoint para recuperar todas as tarefas no sistema.
     * 
     * @return EN: List of all tasks / PT: Lista de todas as tarefas
     */
    @GetMapping
    public ResponseEntity<List<ProjectTaskDTO>> getAllTasks() {
        List<ProjectTaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get task by ID
     * 
     * EN: Endpoint to retrieve a specific task by its ID.
     * 
     * PT: Endpoint para recuperar uma tarefa específica pelo seu ID.
     * 
     * @param id EN: The task ID / PT: O ID da tarefa
     * @return EN: The requested task / PT: A tarefa solicitada
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectTaskDTO> getTaskById(@PathVariable UUID id) {
        ProjectTaskDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Update task
     * 
     * EN: Endpoint to update an existing task.
     * 
     * PT: Endpoint para atualizar uma tarefa existente.
     * 
     * @param id EN: The ID of the task to update / PT: O ID da tarefa a ser atualizada
     * @param taskDTO EN: The updated task data / PT: Os dados atualizados da tarefa
     * @return EN: The updated task / PT: A tarefa atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectTaskDTO> updateTask(@PathVariable UUID id, @Valid @RequestBody ProjectTaskDTO taskDTO) {
        ProjectTaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Delete task
     * 
     * EN: Endpoint to delete a task from the system.
     * 
     * PT: Endpoint para excluir uma tarefa do sistema.
     * 
     * @param id EN: The ID of the task to delete / PT: O ID da tarefa a ser excluída
     * @return EN: HTTP 204 No Content status / PT: Status HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get tasks by project
     * 
     * EN: Endpoint to retrieve all tasks for a specific project.
     * 
     * PT: Endpoint para recuperar todas as tarefas de um projeto específico.
     * 
     * @param projectId EN: The project ID / PT: O ID do projeto
     * @return EN: List of tasks for the project / PT: Lista de tarefas do projeto
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectTaskDTO>> getTasksByProject(@PathVariable UUID projectId) {
        List<ProjectTaskDTO> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by assignee
     * 
     * EN: Endpoint to retrieve all tasks assigned to a specific person.
     * 
     * PT: Endpoint para recuperar todas as tarefas atribuídas a uma pessoa específica.
     * 
     * @param assignedToId EN: The assignee ID / PT: O ID da pessoa designada
     * @return EN: List of tasks for the assignee / PT: Lista de tarefas da pessoa designada
     */
    @GetMapping("/assignee/{assignedToId}")
    public ResponseEntity<List<ProjectTaskDTO>> getTasksByAssignee(@PathVariable UUID assignedToId) {
        List<ProjectTaskDTO> tasks = taskService.getTasksByAssignee(assignedToId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by status
     * 
     * EN: Endpoint to retrieve all tasks with a specific status.
     * 
     * PT: Endpoint para recuperar todas as tarefas com um status específico.
     * 
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of tasks with the specified status / PT: Lista de tarefas com o status especificado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectTaskDTO>> getTasksByStatus(@PathVariable String status) {
        List<ProjectTaskDTO> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get tasks by project and status
     * 
     * EN: Endpoint to retrieve all tasks for a specific project with a specific status.
     * 
     * PT: Endpoint para recuperar todas as tarefas de um projeto específico com um status específico.
     * 
     * @param projectId EN: The project ID / PT: O ID do projeto
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of tasks for the project with the specified status / PT: Lista de tarefas do projeto com o status especificado
     */
    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<ProjectTaskDTO>> getTasksByProjectAndStatus(
            @PathVariable UUID projectId, 
            @PathVariable String status) {
        List<ProjectTaskDTO> tasks = taskService.getTasksByProjectAndStatus(projectId, status);
        return ResponseEntity.ok(tasks);
    }
}
