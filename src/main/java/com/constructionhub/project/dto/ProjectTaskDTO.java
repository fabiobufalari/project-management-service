package com.constructionhub.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project Task Data Transfer Object
 * 
 * EN: This DTO is used for transferring project task data between the service layer and controllers.
 * It includes validation constraints and represents the project task entity in API operations.
 * 
 * PT: Este DTO é usado para transferir dados de tarefas de projeto entre a camada de serviço e os controladores.
 * Inclui restrições de validação e representa a entidade de tarefa de projeto nas operações da API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskDTO {
    /**
     * EN: Unique identifier for the task
     * PT: Identificador único para a tarefa
     */
    private UUID id;
    
    /**
     * EN: Name of the task (required field)
     * PT: Nome da tarefa (campo obrigatório)
     */
    @NotBlank(message = "Nome da tarefa é obrigatório")
    private String name;
    
    /**
     * EN: Detailed description of the task
     * PT: Descrição detalhada da tarefa
     */
    private String description;
    
    /**
     * EN: Reference to the project this task belongs to (required field)
     * PT: Referência ao projeto ao qual esta tarefa pertence (campo obrigatório)
     */
    @NotNull(message = "ID do projeto é obrigatório")
    private UUID projectId;
    
    /**
     * EN: Reference to the person assigned to this task
     * PT: Referência à pessoa designada para esta tarefa
     */
    private UUID assignedToId;
    
    /**
     * EN: Date and time when the task should start
     * PT: Data e hora em que a tarefa deve começar
     */
    private LocalDateTime startDate;
    
    /**
     * EN: Deadline for task completion
     * PT: Prazo para conclusão da tarefa
     */
    private LocalDateTime dueDate;
    
    /**
     * EN: Actual date and time when the task was completed
     * PT: Data e hora real em que a tarefa foi concluída
     */
    private LocalDateTime completedDate;
    
    /**
     * EN: Current status of the task
     * PT: Status atual da tarefa
     */
    private String status;
    
    /**
     * EN: Priority level of the task
     * PT: Nível de prioridade da tarefa
     */
    private Integer priority;
    
    /**
     * EN: Percentage of task completion (0-100)
     * PT: Percentual de conclusão da tarefa (0-100)
     */
    private Integer completionPercentage;
    
    /**
     * EN: Flag indicating if the task is active
     * PT: Indicador se a tarefa está ativa
     */
    private boolean active;
}
