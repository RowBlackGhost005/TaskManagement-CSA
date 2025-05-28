package com.marin.TaskManagement.service;

import com.marin.TaskManagement.dto.LoginResponseDTO;
import com.marin.TaskManagement.dto.UserAuthDTO;
import com.marin.TaskManagement.exception.NoUserFoundException;

/**
 * Defines the Authentication operations available
 */
public interface AuthService {

    LoginResponseDTO authUser(UserAuthDTO userAuth) throws NoUserFoundException;
}
