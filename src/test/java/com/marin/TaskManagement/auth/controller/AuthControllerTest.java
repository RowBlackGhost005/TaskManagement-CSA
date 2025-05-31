package com.marin.TaskManagement.auth.controller;

import com.marin.TaskManagement.common.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testRegister_Post() throws Exception {
        User user = new User(1 , "New User" , "passwordencrypted" , null , null);

        //when(userService.registerUser(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"New User\" , \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Created user "+ user.getUsername() + " successfully"));
    }

    @Test
    public void testLogin_Post() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\" , \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
}
