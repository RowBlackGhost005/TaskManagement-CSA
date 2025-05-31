package com.marin.TaskManagement.user.service;

import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.common.entity.Role;
import com.marin.TaskManagement.common.exception.NoRoleFoundException;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.user.repository.RoleRepository;
import com.marin.TaskManagement.user.repository.UserRepository;
import com.marin.TaskManagement.common.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest(properties = "spring.profiles.active=test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    public void testRegisterUser_Success() throws NoRoleFoundException {
        //Arrange role fetching inside registerUser()
        Role mockRole = new Role(1 , "USER");
        Mockito.when(roleRepository.findByName("USER")).thenReturn(Optional.of(mockRole));

        //Arrange User
        User mockUser = new User(1 , "admin" , "psswrd", new HashSet<>(0) , new ArrayList<>(0));
        mockUser.getRoles().add(mockRole);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        //Act
        User user = userService.registerUser(new UserAuthDTO("admin" , "psswrd"));

        //Assert
        assertNotNull(user);
        assertEquals(1 , user.getId());
        assertEquals("USER" , user.getRoles().stream().map(Role::getName).findFirst().orElse(null));
    }

    @Test
    public void testRegisterUser_Fail_FetchRole(){
        User mockUser = new User(1 , "admin" , "psswrd", new HashSet<>(0) , new ArrayList<>(0));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(mockUser);

        Exception exception = assertThrows(NoRoleFoundException.class , () -> {
            User user = userService.registerUser(new UserAuthDTO("admin" , "psswrd"));
        });
    }

    @Test
    public void testFetchUserByUsername_Success() throws NoUserFoundException {
        User mockUser = new User(1 , "TestUser" , "adfhadryarhgsdruysry", new HashSet<>(0) , new ArrayList<>(0));

        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockUser));

        User user = userService.fetchUserByUsername("TestUser");

        assertNotNull(user);
        assertEquals("TestUser" , user.getUsername());
    }

    @Test
    public void testFetchUserByUsername_Fail(){
        Exception exception = assertThrows(NoUserFoundException.class , () -> {
           User user = userService.fetchUserByUsername("Row");
        });
    }

    @Test
    public void testFetchUserById_Success() throws NoUserFoundException {
        UserDTO mockUser = new UserDTO(1 , "Luis");

        Mockito.when(userRepository.findUserById(1)).thenReturn(Optional.of(mockUser));

        UserDTO user = userService.fetchUserById(1);

        assertNotNull(user);
        assertEquals(1 , user.id());
        assertEquals("Luis" , user.username());
    }

    @Test
    public void testFetchUserTaskCount_Success() {
        UserTaskCountDTO userTaskCount = new UserTaskCountDTO(1 , "Admin" , 1);
        ArrayList<UserTaskCountDTO> usersTaskCount = new ArrayList<>();

        usersTaskCount.add(userTaskCount);

        Mockito.when(userRepository.fetchUsersTaskCount()).thenReturn(Optional.of(usersTaskCount));

        List<UserTaskCountDTO> registerUsersTaskCount = userService.fetchUsersTaskCount();

        assertNotNull(registerUsersTaskCount);
        assertEquals(1 , registerUsersTaskCount.size());
        assertEquals(1 , registerUsersTaskCount.getFirst().id());
    }

}
