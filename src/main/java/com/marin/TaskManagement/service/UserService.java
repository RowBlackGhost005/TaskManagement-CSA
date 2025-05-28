package com.marin.TaskManagement.service;

import com.marin.TaskManagement.dto.UserAuthDTO;
import com.marin.TaskManagement.entity.User;
import com.marin.TaskManagement.exception.NoUserFoundException;

/**
 * Interface for User related operations
 */
public interface UserService {

    User registerUser(UserAuthDTO userAuth);

    User fetchUserByUsername(String username) throws NoUserFoundException;
}
