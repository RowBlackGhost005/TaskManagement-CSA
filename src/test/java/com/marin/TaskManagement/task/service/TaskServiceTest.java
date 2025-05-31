package com.marin.TaskManagement.task.service;

import com.marin.TaskManagement.auth.service.JwtService;
import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import com.marin.TaskManagement.common.dto.TaskRegisterDTO;
import com.marin.TaskManagement.common.entity.Priority;
import com.marin.TaskManagement.common.entity.Status;
import com.marin.TaskManagement.common.entity.Task;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;
import com.marin.TaskManagement.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
public class TaskServiceTest {

    @MockitoBean
    private TaskRepository taskRepository;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    @InjectMocks
    @Spy
    private TaskServiceImp taskService;

    @Test
    @Transactional
    public void testCreateTask_Success(){
        User user = new User();
        user.setId(1);
        Task taskMock = new Task(1 , user , "Task One" , "Description" , Status.PENDING , Priority.LOW , LocalDateTime.now());

        Mockito.when(jwtService.extractAuthUserId(Mockito.any())).thenReturn(1);

        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(taskMock);

        TaskInfoDTO task = taskService.createTask(new TaskRegisterDTO("Task One" , "Task Test", Status.PENDING , Priority.LOW , LocalDateTime.now()));

        assertNotNull(task);
        assertEquals(1 , task.id());
        assertNotNull(task.title());
    }

    @Test
    @Transactional
    public void testUpdateTask_Success() throws NoTaskFoundException {
        User user = new User();
        user.setId(1);
        Task taskMock = new Task(1 , user , "Task One" , "Description" , Status.PENDING , Priority.LOW , LocalDateTime.now());

        Mockito.when(jwtService.extractAuthUserId(Mockito.any())).thenReturn(1);
        Mockito.when(taskRepository.fetchTaskByIdUserId(Mockito.anyInt() , Mockito.anyInt())).thenReturn(Optional.of(taskMock));
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(taskMock);

        TaskRegisterDTO taskDataUpdate = new TaskRegisterDTO("Updated Task" , null , null , null , null);

        TaskInfoDTO taskInfoDTO = taskService.updateTask(1 , taskDataUpdate);

        assertNotNull(taskInfoDTO);
        assertEquals("Updated Task" , taskInfoDTO.title());
    }

    @Test
    @Transactional
    public void testGetTaskById_Success() {
        User user = new User();
        user.setId(1);
        Task taskMock = new Task(1 , user , "Task One" , "Description" , Status.PENDING , Priority.LOW , LocalDateTime.now());

        Mockito.when(taskRepository.fetchTaskWithUser(1)).thenReturn(Optional.of(taskMock));

        TaskDTO task = taskService.getTaskById(1);

        assertNotNull(task);
        assertEquals(1 , task.id());
        assertEquals("Task One" , task.title());

    }
}
