package com.marin.TaskManagement.common.dto;

public record UserDTO(
        int id,
        String username
){

    public UserDTO(int id , String username) {
        this.id = id;
        this.username = username;
    }

}
