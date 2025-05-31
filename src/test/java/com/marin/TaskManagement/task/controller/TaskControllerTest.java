package com.marin.TaskManagement.task.controller;

import com.marin.TaskManagement.auth.security.JwtUtil;
import com.marin.TaskManagement.common.entity.*;
import com.marin.TaskManagement.config.SecurityConfig;
import com.marin.TaskManagement.user.repository.RoleRepository;
import com.marin.TaskManagement.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static String JWT;

    private static User adminUser;

    private static Task adminTask;

    @BeforeEach
    public void setup(){
        //Enabling web security
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //Pre persisting known admin role for using its JWT token
        Role role = roleRepository.findByName("ADMIN").orElseThrow();
        User user = new User(0 , "testAdmin" , passwordEncoder.encode("password") , new HashSet<>() , new ArrayList<>());
        user.getRoles().add(role);

        entityManager.persist(user);

        Task task = new Task(0 , user , "Task One" , "Description" , Status.PENDING , Priority.LOW  , LocalDateTime.now());

        entityManager.persist(task);
        entityManager.flush();

        adminTask = task;

        adminUser = user;

        JWT = jwtUtil.generateToken(user);
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testCreateTask_Success() throws Exception {

        String jsonPayload = "{"
                + "\"title\":\"Do Homework\","
                + "\"description\":\"CSA Homework\","
                + "\"status\":\"PENDING\","
                + "\"priority\":\"LOW\","
                + "\"dueDate\":\"2025-05-28T20:30:00\""
                + "}";


        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                        .header("Authorization" , "Bearer " + JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testGetAuthUserTasks_Success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .header("Authorization" , "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testUpdateTask_Success() throws Exception {
        String jsonPayload = "{"
                + "\"status\":\"COMPLETED\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/" + adminTask.getId())
                        .header("Authorization" , "Bearer " + JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testDeleteTask_Response() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + adminTask.getId())
                        .header("Authorization" , "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testDeleteTask_NotOwningTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + 3)
                        .header("Authorization" , "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isNotFound());
        //It should respond 404 due to it not founding in this user the task with the id 3
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testGetTaskByIdAdmin_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/admin/task/" + 3)
                        .header("Authorization" , "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
                //.andExpect(jsonPath("$.user.username").value(not("testAdmin")));
    }

    @Test
    @WithMockUser(username = "testAdmin", roles = {"ADMIN"})
    @Transactional
    public void testDeleteTaskByAdmin_Successs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/admin/task/" + 3)
                        .header("Authorization" , "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
