package com.marin.TaskManagement.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Used for response only at a success authentication attempt")
public record LoginResponseDTO (
        @Schema(description = "JWT Token" , example = "aw34o58iyan9486sjrhgs890rhtsrh5s689y4ns89")
        String accessToken,
        @Schema(description = "Defines the type of this token" , example = "Bearer")
        String tokenType
){ }
