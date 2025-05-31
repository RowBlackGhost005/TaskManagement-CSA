package com.marin.TaskManagement.user.repository;

import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(properties = "spring.profiles.active=test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_Save_ReturnSavedUser(){
        //Arrange
        User user = new User ();
        user.setUsername("Admin");
        user.setPassword("pssword");
        user.setRoles(new HashSet<>());
        user.setTasks(new ArrayList<>());

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFetchUsersTaskCount_Success(){
        List<UserTaskCountDTO> userTaskCount = userRepository.fetchUsersTaskCount().orElseThrow();

        Assertions.assertThat(userTaskCount).isNotNull();
        Assertions.assertThat(userTaskCount.getFirst().id()).isGreaterThan(0);
    }

}
