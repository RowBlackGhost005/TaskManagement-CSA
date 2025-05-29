package com.marin.TaskManagement.common.dto;

public record UserTaskCountDTO (
        int id,
        String username,
        long taskCount
) { }
