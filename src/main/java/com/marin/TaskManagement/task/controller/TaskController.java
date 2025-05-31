package com.marin.TaskManagement.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;
import com.marin.TaskManagement.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Creates a Task for the current user",
            description = "Creates the given task and attach it to the current authenticated User. Requires an User Token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskInfoDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskInfoDTO> createTask(
            @RequestBody @Parameter(description = "Task to register" , required = true)
            @Schema(description = "TaskRegisterDTO containing all the information of the task") TaskRegisterDTO taskRegisterDTO) throws Exception {
        TaskInfoDTO task = taskService.createTask(taskRegisterDTO);

        return ResponseEntity.ok(task);
    }

    @Operation(
            summary = "Fetch all User tasks",
            description = "Fetch all current auth User tasks and returns them. Requires User token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskInfoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found for current user")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TaskInfoDTO>> getTasksUser(){
        List<TaskInfoDTO> userTasks = taskService.getAuthUserTasks();

        return ResponseEntity.ok(userTasks);
    }

    @Operation(
            summary = "Updates a task information",
            description = "Updates a task using the fully or partially task sent only if the User owns such task . Requires User token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskInfoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found for current user")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskInfoDTO> updateTask(
            @PathVariable @Parameter(description = "Id of the task to update" , example = "1") int taskId ,
            @RequestBody @Parameter(description = "Updated data" , required = true)
            @Schema(description = "TaskRegisterDTO containing only the data to update") TaskRegisterDTO taskRegisterDTO)
            throws NoTaskFoundException {
        TaskInfoDTO updatedTask = taskService.updateTask(taskId , taskRegisterDTO);

        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Deletes a task",
            description = "Deletes a task whose ID matches the given. It only deletes Task that are owned by the User. Requires User token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task Deleted",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "No tasks found for current user")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteTask(
            @PathVariable @Parameter(description = "Id of the task to delete") int taskId){

        taskService.deleteTask(taskId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(
            summary = "Fetch all registered task. (ADMIN)",
            description = "Fetch all tasks registered in this API. Requires Admin token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping("/admin/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDTO>> showAllTasks(){
        List<TaskDTO> tasks = taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Fetch a task by its ID. (ADMIN)",
            description = "Fetch a single complete Task by its ID. Requires Admin token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping("/admin/task/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> getTaskById(
            @PathVariable @Parameter(description = "Id of the task to fetch") int taskId) throws JsonProcessingException {
        TaskDTO task = taskService.getTaskById(taskId);

        return ResponseEntity.ok(task);
    }

    @Operation(
            summary = "Deletes any task with its ID. (ADMIN)",
            description = "Deletes a single Task using its ID no matter the owner. Requires Admin token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No tasks found")
            },
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @DeleteMapping("/admin/task/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTaskById(
            @PathVariable @Parameter(description = "Id of the task to delete") int taskId){
        taskService.deleteTaskAdmin(taskId);
        return ResponseEntity.ok("Task deleted");
    }
}
