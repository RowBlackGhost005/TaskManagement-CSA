package com.marin.TaskManagement.auth.service;

import com.marin.TaskManagement.common.dto.LoginResponseDTO;
import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.exception.NoUserFoundException;

/**
 * Defines the Authentication operations available
 */
public interface AuthService {

    LoginResponseDTO authUser(UserAuthDTO userAuth) throws NoUserFoundException;
}
