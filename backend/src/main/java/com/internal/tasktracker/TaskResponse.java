package com.internal.tasktracker;

import java.time.LocalDateTime;

// API view of a task: an explicit allowlist of fields, decoupled from the JPA entity
public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        String assignee,
        LocalDateTime createdAt) {

    static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.getStatus(), task.getPriority(), task.getAssignee(), task.getCreatedAt());
    }
}
