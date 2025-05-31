package com.marin.TaskManagement.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Basic data of the User")
public record UserDTO(
        @Schema(description = "Unique ID of the User" , example = "6")
        int id,
        @Schema(description = "Username of the User" , example = "Jhon")
        String username
){

    public UserDTO(int id , String username) {
        this.id = id;
        this.username = username;
    }

}
