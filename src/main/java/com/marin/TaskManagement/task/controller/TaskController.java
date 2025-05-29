package com.marin.TaskManagement.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.task.service.TaskService;
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

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> createTask(@RequestBody TaskRegisterDTO taskRegisterDTO) throws Exception {
        Task task = taskService.createTask(taskRegisterDTO);

        return ResponseEntity.ok(task);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Task>> getTasksUser(){
        List<Task> userTasks = taskService.getAuthUserTasks();

        return ResponseEntity.ok(userTasks);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId , @RequestBody TaskRegisterDTO taskRegisterDTO){
        Task updatedTask = taskService.updateTask(taskId , taskRegisterDTO);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId){

        taskService.deleteTask(taskId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/admin/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> showAllTasks(){
        List<Task> tasks = taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/admin/task/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int taskId) throws JsonProcessingException {
        TaskDTO task = taskService.getTaskById(taskId);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/admin/task/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTaskById(@PathVariable int taskId){
        taskService.deleteTaskAdmin(taskId);
        return ResponseEntity.ok("Task deleted");
    }
}
