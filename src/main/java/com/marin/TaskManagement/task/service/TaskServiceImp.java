package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.auth.service.JwtService;
import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.entity.Priority;
import com.marin.TaskManagement.common.entity.Status;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImp implements TaskService{

    private final TaskRepository taskRepository;

    private final JwtService jwtService;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.jwtService = jwtService;
    }


    @Override
    public Task createTask(TaskRegisterDTO registerTask) throws Exception {
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

        return taskRepository.save(taskDB);
    }

    @Override
    public List<Task> getAuthUserTasks() {

        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        return taskRepository.fetchTaskByUserId(authUserId).orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public Task updateTask(int id, TaskRegisterDTO taskRegister) {

        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        Task taskDB = taskRepository.fetchTaskByIdUserId(id , authUserId).orElseThrow();

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

        return taskRepository.save(taskDB);
    }

    @Override
    public void deleteTask(int id) {
        int authUserId = jwtService.extractAuthUserId(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        Task taskDB = taskRepository.fetchTaskByIdUserId(id, authUserId).orElseThrow();

        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public TaskDTO getTaskById(int id) {

        Task task = taskRepository.fetchTaskWithUser(id).orElseThrow();

        UserDTO userDTO = new UserDTO(task.getUser().getId() , task.getUser().getUsername());

        return new TaskDTO(task.getId() , userDTO , task.getTitle() , task.getDescription() , task.getStatus() , task.getPriority() , task.getDueDate());
    }

    @Override
    public void deleteTaskAdmin(int id) {
        taskRepository.deleteById(id);
    }
}
