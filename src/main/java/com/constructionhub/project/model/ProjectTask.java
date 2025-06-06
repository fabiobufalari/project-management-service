package com.constructionhub.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project Task Entity
 * 
 * EN: Represents a task or activity within a construction project.
 * This entity stores information about individual tasks including assignment,
 * timeline, status, and completion percentage.
 * 
 * PT: Representa uma tarefa ou atividade dentro de um projeto de construção.
 * Esta entidade armazena informações sobre tarefas individuais, incluindo atribuição,
 * cronograma, status e percentual de conclusão.
 */
@Entity
@Table(name = "project_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTask {

    /**
     * EN: Unique identifier for the task
     * PT: Identificador único para a tarefa
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * EN: Name of the task
     * PT: Nome da tarefa
     */
    @Column(nullable = false)
    private String name;

    /**
     * EN: Detailed description of the task
     * PT: Descrição detalhada da tarefa
     */
    @Column
    private String description;

    /**
     * EN: Reference to the project this task belongs to
     * PT: Referência ao projeto ao qual esta tarefa pertence
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    /**
     * EN: Reference to the person assigned to this task
     * PT: Referência à pessoa designada para esta tarefa
     */
    @Column(name = "assigned_to")
    private UUID assignedToId;

    /**
     * EN: Date and time when the task should start
     * PT: Data e hora em que a tarefa deve começar
     */
    @Column
    private LocalDateTime startDate;

    /**
     * EN: Deadline for task completion
     * PT: Prazo para conclusão da tarefa
     */
    @Column
    private LocalDateTime dueDate;

    /**
     * EN: Actual date and time when the task was completed
     * PT: Data e hora real em que a tarefa foi concluída
     */
    @Column
    private LocalDateTime completedDate;

    /**
     * EN: Current status of the task (e.g., TODO, IN_PROGRESS, COMPLETED)
     * PT: Status atual da tarefa (ex: A_FAZER, EM_ANDAMENTO, CONCLUÍDA)
     */
    @Column
    private String status;

    /**
     * EN: Priority level of the task (e.g., 1=highest, 5=lowest)
     * PT: Nível de prioridade da tarefa (ex: 1=mais alta, 5=mais baixa)
     */
    @Column
    private Integer priority;

    /**
     * EN: Percentage of task completion (0-100)
     * PT: Percentual de conclusão da tarefa (0-100)
     */
    @Column
    private Integer completionPercentage;

    /**
     * EN: Flag indicating if the task is active
     * PT: Indicador se a tarefa está ativa
     */
    @Column(name = "is_active")
    private boolean active = true;

    /**
     * EN: Timestamp when the task record was created
     * PT: Timestamp de quando o registro da tarefa foi criado
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * EN: Timestamp when the task record was last updated
     * PT: Timestamp de quando o registro da tarefa foi atualizado pela última vez
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
