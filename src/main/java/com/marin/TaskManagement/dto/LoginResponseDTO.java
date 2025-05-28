package com.marin.TaskManagement.dto;

public record LoginResponseDTO (
        String accessToken,
        String tokenType
){ }
