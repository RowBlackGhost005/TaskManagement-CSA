package com.marin.TaskManagement.user.service;

import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.dto.UserTaskCountDTO;
import com.marin.TaskManagement.common.dto.UserTasksDTO;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoUserFoundException;

import java.util.List;

/**
 * Interface for User related operations
 */
public interface UserService {

    User registerUser(UserAuthDTO userAuth);

    User fetchUserByUsername(String username) throws NoUserFoundException;

    UserDTO fetchUserById(int id) throws Exception;

    List<UserTaskCountDTO> fetchUsersTaskCount();
}
