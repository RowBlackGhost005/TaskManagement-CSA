package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;

import java.util.List;

public interface TaskService {

    TaskInfoDTO createTask(TaskRegisterDTO registerTask);

    List<TaskInfoDTO> getAuthUserTasks();

    TaskInfoDTO updateTask(int id , TaskRegisterDTO taskRegister) throws NoTaskFoundException;

    void deleteTask(int id);

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(int id);

    void deleteTaskAdmin(int id);
}
