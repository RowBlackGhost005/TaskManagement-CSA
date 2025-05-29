package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRegisterDTO registerTask);

    List<Task> getAuthUserTasks();

    Task updateTask(int id , TaskRegisterDTO taskRegister) throws NoTaskFoundException;

    void deleteTask(int id);

    List<Task> getAllTasks();

    TaskDTO getTaskById(int id);

    void deleteTaskAdmin(int id);
}
