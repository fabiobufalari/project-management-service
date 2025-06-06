package com.constructionhub.project.service;

import com.constructionhub.project.dto.ProjectTaskDTO;
import com.constructionhub.project.exception.ResourceAlreadyExistsException;
import com.constructionhub.project.exception.ResourceNotFoundException;
import com.constructionhub.project.model.ProjectTask;
import com.constructionhub.project.repository.ProjectTaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Project Task Service
 * 
 * EN: Service class that handles business logic for project task operations.
 * This service provides methods for creating, retrieving, updating, and deleting project tasks,
 * as well as specialized operations for task management.
 * 
 * PT: Classe de serviço que lida com a lógica de negócios para operações de tarefas de projeto.
 * Este serviço fornece métodos para criar, recuperar, atualizar e excluir tarefas de projeto,
 * bem como operações especializadas para gerenciamento de tarefas.
 */
@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    /**
     * EN: Constructor with dependency injection
     * PT: Construtor com injeção de dependência
     * 
     * @param projectTaskRepository EN: The project task repository / PT: O repositório de tarefas de projeto
     */
    @Autowired
    public ProjectTaskService(ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }

    /**
     * Create a new project task
     * 
     * EN: Creates a new task for a project.
     * 
     * PT: Cria uma nova tarefa para um projeto.
     * 
     * @param taskDTO EN: The task data transfer object / PT: O objeto de transferência de dados da tarefa
     * @return EN: The created task / PT: A tarefa criada
     */
    public ProjectTaskDTO createTask(ProjectTaskDTO taskDTO) {
        ProjectTask task = new ProjectTask();
        BeanUtils.copyProperties(taskDTO, task);
        ProjectTask savedTask = projectTaskRepository.save(task);
        
        ProjectTaskDTO savedTaskDTO = new ProjectTaskDTO();
        BeanUtils.copyProperties(savedTask, savedTaskDTO);
        return savedTaskDTO;
    }

    /**
     * Get all tasks
     * 
     * EN: Retrieves all tasks in the system.
     * 
     * PT: Recupera todas as tarefas no sistema.
     * 
     * @return EN: List of all tasks / PT: Lista de todas as tarefas
     */
    public List<ProjectTaskDTO> getAllTasks() {
        List<ProjectTask> tasks = projectTaskRepository.findAll();
        return tasks.stream()
                .map(task -> {
                    ProjectTaskDTO taskDTO = new ProjectTaskDTO();
                    BeanUtils.copyProperties(task, taskDTO);
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get task by ID
     * 
     * EN: Retrieves a specific task by its unique identifier.
     * 
     * PT: Recupera uma tarefa específica pelo seu identificador único.
     * 
     * @param id EN: The task ID / PT: O ID da tarefa
     * @return EN: The requested task / PT: A tarefa solicitada
     * @throws ResourceNotFoundException EN: If the task is not found / PT: Se a tarefa não for encontrada
     */
    public ProjectTaskDTO getTaskById(UUID id) {
        ProjectTask task = projectTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        
        ProjectTaskDTO taskDTO = new ProjectTaskDTO();
        BeanUtils.copyProperties(task, taskDTO);
        return taskDTO;
    }

    /**
     * Update task
     * 
     * EN: Updates an existing task with new information.
     * 
     * PT: Atualiza uma tarefa existente com novas informações.
     * 
     * @param id EN: The ID of the task to update / PT: O ID da tarefa a ser atualizada
     * @param taskDTO EN: The updated task data / PT: Os dados atualizados da tarefa
     * @return EN: The updated task / PT: A tarefa atualizada
     * @throws ResourceNotFoundException EN: If the task is not found / PT: Se a tarefa não for encontrada
     */
    public ProjectTaskDTO updateTask(UUID id, ProjectTaskDTO taskDTO) {
        ProjectTask task = projectTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        
        BeanUtils.copyProperties(taskDTO, task, "id", "createdAt");
        ProjectTask updatedTask = projectTaskRepository.save(task);
        
        ProjectTaskDTO updatedTaskDTO = new ProjectTaskDTO();
        BeanUtils.copyProperties(updatedTask, updatedTaskDTO);
        return updatedTaskDTO;
    }

    /**
     * Delete task
     * 
     * EN: Deletes a task from the system.
     * 
     * PT: Exclui uma tarefa do sistema.
     * 
     * @param id EN: The ID of the task to delete / PT: O ID da tarefa a ser excluída
     * @throws ResourceNotFoundException EN: If the task is not found / PT: Se a tarefa não for encontrada
     */
    public void deleteTask(UUID id) {
        ProjectTask task = projectTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        
        projectTaskRepository.delete(task);
    }

    /**
     * Get tasks by project
     * 
     * EN: Retrieves all tasks associated with a specific project.
     * 
     * PT: Recupera todas as tarefas associadas a um projeto específico.
     * 
     * @param projectId EN: The project ID / PT: O ID do projeto
     * @return EN: List of tasks for the project / PT: Lista de tarefas do projeto
     */
    public List<ProjectTaskDTO> getTasksByProject(UUID projectId) {
        List<ProjectTask> tasks = projectTaskRepository.findByProjectId(projectId);
        return tasks.stream()
                .map(task -> {
                    ProjectTaskDTO taskDTO = new ProjectTaskDTO();
                    BeanUtils.copyProperties(task, taskDTO);
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by assignee
     * 
     * EN: Retrieves all tasks assigned to a specific person.
     * 
     * PT: Recupera todas as tarefas atribuídas a uma pessoa específica.
     * 
     * @param assignedToId EN: The assignee ID / PT: O ID da pessoa designada
     * @return EN: List of tasks for the assignee / PT: Lista de tarefas da pessoa designada
     */
    public List<ProjectTaskDTO> getTasksByAssignee(UUID assignedToId) {
        List<ProjectTask> tasks = projectTaskRepository.findByAssignedToId(assignedToId);
        return tasks.stream()
                .map(task -> {
                    ProjectTaskDTO taskDTO = new ProjectTaskDTO();
                    BeanUtils.copyProperties(task, taskDTO);
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by status
     * 
     * EN: Retrieves all tasks with a specific status.
     * 
     * PT: Recupera todas as tarefas com um status específico.
     * 
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of tasks with the specified status / PT: Lista de tarefas com o status especificado
     */
    public List<ProjectTaskDTO> getTasksByStatus(String status) {
        List<ProjectTask> tasks = projectTaskRepository.findByStatus(status);
        return tasks.stream()
                .map(task -> {
                    ProjectTaskDTO taskDTO = new ProjectTaskDTO();
                    BeanUtils.copyProperties(task, taskDTO);
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by project and status
     * 
     * EN: Retrieves all tasks for a specific project with a specific status.
     * 
     * PT: Recupera todas as tarefas de um projeto específico com um status específico.
     * 
     * @param projectId EN: The project ID / PT: O ID do projeto
     * @param status EN: The status to filter by / PT: O status para filtrar
     * @return EN: List of tasks for the project with the specified status / PT: Lista de tarefas do projeto com o status especificado
     */
    public List<ProjectTaskDTO> getTasksByProjectAndStatus(UUID projectId, String status) {
        List<ProjectTask> tasks = projectTaskRepository.findByProjectIdAndStatus(projectId, status);
        return tasks.stream()
                .map(task -> {
                    ProjectTaskDTO taskDTO = new ProjectTaskDTO();
                    BeanUtils.copyProperties(task, taskDTO);
                    return taskDTO;
                })
                .collect(Collectors.toList());
    }
}
