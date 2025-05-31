package com.marin.TaskManagement.common.dto;

import com.marin.TaskManagement.common.entity.Priority;
import com.marin.TaskManagement.common.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Holds a representation of a Task that is stored in this API")
public record TaskInfoDTO (
        @Schema(description = "Unique identifier of the task" , example = "5")
        int id,
        @Schema(description = "Title of the task" , example = "Do homework")
        String title,
        @Schema(description = "Description of the task" , example = "Check book page 5")
        String description,
        @Schema(description = "Define the current Status of this task" , example = "INPROGESS")
        Status status,
        @Schema(description = "Defines the Priority of this task" , example = "HIGH")
        Priority priority,
        @Schema(description = "Defines the time at which this task is Due" , example = "2025-05-31T15:00:00.000")
        LocalDateTime dueDate
){ }
