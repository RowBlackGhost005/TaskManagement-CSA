package com.marin.TaskManagement.task.repository;

import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.common.entity.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFetchTasksByUserId_Success(){

        List<TaskInfoDTO> tasks = taskRepository.fetchTasksByUserId(1).orElseThrow();

        Assertions.assertThat(tasks).isNotNull();
        Assertions.assertThat(tasks.getFirst().id()).isGreaterThan(0);
    }

    @Test
    public void testFetchAllTasks(){

        List<TaskDTO> tasks = taskRepository.fetchAllTasks().orElseThrow();

        Assertions.assertThat(tasks.size()).isGreaterThan(0);
        Assertions.assertThat(tasks.getFirst().user().id()).isGreaterThan(0);
    }

    @Test
    public void testFetchTaskByIdUserId(){

        Task reliableTaskData = taskRepository.findById(1).orElseThrow();

        int taskId = 1;
        int userId = reliableTaskData.getUser().getId();

        Task task = taskRepository.fetchTaskByIdUserId(taskId , userId).orElseThrow();

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(taskId);
        Assertions.assertThat(task.getUser().getId()).isEqualTo(userId);
    }

    @Test
    public void testFetchTaskWithUser(){

        Task task = taskRepository.fetchTaskWithUser(1).orElseThrow();

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getUser()).isNotNull();
        Assertions.assertThat(task.getUser().getId()).isGreaterThan(0);
    }
}
