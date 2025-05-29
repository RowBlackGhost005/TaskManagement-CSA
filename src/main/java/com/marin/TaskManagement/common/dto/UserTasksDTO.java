package com.marin.TaskManagement.common.dto;

import java.util.List;

public record UserTasksDTO (
        int id,
        String username,
        List<TaskInfoDTO> tasks
){ }
