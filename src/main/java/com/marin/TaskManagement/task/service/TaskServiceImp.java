package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.auth.service.JwtService;
import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.entity.Priority;
import com.marin.TaskManagement.common.entity.Status;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;
import com.marin.TaskManagement.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service

public class TaskServiceImp implements TaskService{

    private final TaskRepository taskRepository;

    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImp.class);

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.jwtService = jwtService;
    }


    @Override
    public TaskInfoDTO createTask(TaskRegisterDTO registerTask) {
        Task taskDB = new Task();

        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        User user = new User();
        user.setId(authUserId);
        taskDB.setUser(user);

        taskDB.setTitle(registerTask.title());
        taskDB.setDescription((registerTask.description() != null) ? registerTask.description() : "");
        taskDB.setStatus((registerTask.status() != null) ? registerTask.status() : Status.PENDING);
        taskDB.setPriority((registerTask.priority() != null) ? registerTask.priority() : Priority.LOW);
        taskDB.setDueDate(registerTask.dueDate());

        taskDB = taskRepository.save(taskDB);
        return taskToTaskInfo(taskDB);
    }

    @Override
    public List<TaskInfoDTO> getAuthUserTasks() {

        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());


        return taskRepository.fetchTasksByUserId(authUserId).orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public TaskInfoDTO updateTask(int id, TaskRegisterDTO taskRegister) throws NoTaskFoundException {

        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        Task taskDB = taskRepository.fetchTaskByIdUserId(id , authUserId).orElseThrow( () -> new NoTaskFoundException("No task found with such ID"));

        if(taskRegister.title() != null){
            taskDB.setTitle(taskRegister.title());
        }

        if(taskRegister.description() != null){
            taskDB.setDescription(taskRegister.description());
        }

        if(taskRegister.status() != null){
            taskDB.setStatus(taskRegister.status());
        }

        if(taskRegister.priority() != null){
            taskDB.setPriority(taskRegister.priority());
        }

        if(taskRegister.dueDate() != null){
            taskDB.setDueDate(taskRegister.dueDate());
        }

        taskDB = taskRepository.save(taskDB);

        return taskToTaskInfo(taskDB);
    }

    @Override
    public void deleteTask(int id) {
        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        Task taskDB = taskRepository.fetchTaskByIdUserId(id, authUserId).orElseThrow();

        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        logger.info("Accessed all tasks by: {}",getAuthUser());
        return taskRepository.fetchAllTasks().orElseThrow();
    }

    @Override
    public TaskDTO getTaskById(int id) {

        Task task = taskRepository.fetchTaskWithUser(id).orElseThrow();

        UserDTO userDTO = new UserDTO(task.getUser().getId() , task.getUser().getUsername());

        return new TaskDTO(task.getId() , userDTO , task.getTitle() , task.getDescription() , task.getStatus() , task.getPriority() , task.getDueDate());
    }

    @Override
    public void deleteTaskAdmin(int id) {
        logger.info("Deleting Task with ID: {} By: {}", id , getAuthUser());
        taskRepository.deleteById(id);
    }

    private String getAuthUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private TaskInfoDTO taskToTaskInfo(Task task){
        return new TaskInfoDTO(task.getId() , task.getTitle() , task.getDescription() , task.getStatus() , task.getPriority() , task.getDueDate());
    }
}
