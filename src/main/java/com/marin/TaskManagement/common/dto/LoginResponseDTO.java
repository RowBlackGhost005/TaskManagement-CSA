package com.marin.TaskManagement.common.dto;

public record LoginResponseDTO (
        String accessToken,
        String tokenType
){ }
