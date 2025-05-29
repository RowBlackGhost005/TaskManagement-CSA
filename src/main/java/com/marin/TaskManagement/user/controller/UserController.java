package com.marin.TaskManagement.user.controller;

import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/taskCount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserTaskCountDTO>> fetchAllUsers(){
        List<UserTaskCountDTO> usersTasks = userService.fetchUsersTaskCount();

        return ResponseEntity.ok(usersTasks);
    }


}
