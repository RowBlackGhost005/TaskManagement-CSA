package com.marin.TaskManagement.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entity for showing User data and the amount of Task it owns")
public record UserTaskCountDTO (
        @Schema(description = "Unique ID of the User" , example = "2")
        int id,
        @Schema(description = "Username of this User" , example = "Juan")
        String username,
        @Schema(description = "Count of task owned by this User" , example = "4")
        long taskCount
) { }
