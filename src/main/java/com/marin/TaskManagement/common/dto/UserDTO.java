package com.marin.TaskManagement.common.dto;

import com.marin.TaskManagement.common.entity.Role;

import java.util.HashSet;
import java.util.Set;

public record UserDTO(
        int id,
        String username
){

    public UserDTO(int id , String username) {
        this.id = id;
        this.username = username;
    }

}
