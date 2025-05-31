package com.marin.TaskManagement.user.controller;

import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(
            summary = "Fetch Users task count. (ADMIN)",
            description = "Fetch all Users alongside its Task count without the details. Requires Admin token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserTaskCountDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping("/admin/taskCount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserTaskCountDTO>> fetchAllUsers(){
        List<UserTaskCountDTO> usersTasks = userService.fetchUsersTaskCount();

        return ResponseEntity.ok(usersTasks);
    }


}
