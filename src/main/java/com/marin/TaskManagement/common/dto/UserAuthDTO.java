package com.marin.TaskManagement.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for Authentication Credentials
 */
@Schema(description = "Holds user Authentication data, used only for Incoming requests")
public record UserAuthDTO (
        @Schema(description = "Username to authenticate" , example = "Elizabeth")
       String username,

        @Schema(description = "Password of the User" , example = "mysecurePassword")
       String password
) { }
