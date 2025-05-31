package com.marin.TaskManagement.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Entity for aggregate all task owned of a User")
public record UserTasksDTO (
        @Schema(description = "Unique User ID" , example = "1")
        int id,
        @Schema(description = "Username of the User" , example = "Admin")
        String username,
        @Schema(description = "List of Task owned by this User")
        List<TaskInfoDTO> tasks
){ }
