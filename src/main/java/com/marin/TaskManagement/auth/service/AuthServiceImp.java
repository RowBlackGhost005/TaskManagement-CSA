package com.marin.TaskManagement.auth.service;

import com.marin.TaskManagement.common.dto.LoginResponseDTO;
import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.auth.security.JwtUtil;
import com.marin.TaskManagement.task.service.TaskServiceImp;
import com.marin.TaskManagement.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService{


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImp.class);

    @Autowired
    public AuthServiceImp(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponseDTO authUser(UserAuthDTO userAuth) throws NoUserFoundException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuth.username() , userAuth.password()));

        User user = userService.fetchUserByUsername(userAuth.username());

        String jwtToken = jwtUtil.generateToken(user);

        logger.info("Logged User {} with ID {}" , user.getUsername() , user.getId());

        return new LoginResponseDTO(jwtToken , "Bearer");
    }
}
