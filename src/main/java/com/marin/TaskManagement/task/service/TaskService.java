package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(TaskRegisterDTO registerTask) throws Exception;

    List<Task> getAuthUserTasks();

    Task updateTask(int id , TaskRegisterDTO taskRegister);

    void deleteTask(int id);
}
