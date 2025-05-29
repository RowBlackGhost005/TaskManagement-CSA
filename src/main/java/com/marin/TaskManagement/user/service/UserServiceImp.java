package com.marin.TaskManagement.user.service;

import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.common.dto.UserTasksDTO;
import com.marin.TaskManagement.common.entity.Role;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.user.repository.RoleRepository;
import com.marin.TaskManagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of UserService for managing operation related to Users
 */
@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final String defaultRole = "USER";

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public User registerUser(UserAuthDTO userAuth) {
        User user = new User();
        user.setUsername(userAuth.username());
        user.setPassword(passwordEncoder.encode(userAuth.password()));

        Role userRole = roleRepository.findByName(defaultRole).orElseThrow();

        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    @Override
    public User fetchUserByUsername(String username) throws NoUserFoundException {
        return userRepository.findByUsername(username).orElseThrow( () -> new NoUserFoundException("There is no user with such username"));
    }

    @Override
    public UserDTO fetchUserById(int id) throws Exception {
        return userRepository.findUserById(id).orElseThrow( () -> new Exception("Not found"));
    }

    @Override
    public List<UserTaskCountDTO> fetchUsersTaskCount() {
        return userRepository.fetchUsersTaskCount().orElseThrow();
    }
}
