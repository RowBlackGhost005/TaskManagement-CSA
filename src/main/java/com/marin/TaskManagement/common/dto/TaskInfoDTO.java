package com.marin.TaskManagement.common.dto;

import com.marin.TaskManagement.common.entity.Priority;
import com.marin.TaskManagement.common.entity.Status;

import java.time.LocalDateTime;

public record TaskInfoDTO (
        int id,
        String title,
        String description,
        Status status,
        Priority priority,
        LocalDateTime dueDate
){ }
