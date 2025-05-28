package com.marin.TaskManagement.service;

import com.marin.TaskManagement.dto.LoginResponseDTO;
import com.marin.TaskManagement.dto.UserAuthDTO;
import com.marin.TaskManagement.entity.User;
import com.marin.TaskManagement.exception.NoUserFoundException;
import com.marin.TaskManagement.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService{


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

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

        return new LoginResponseDTO(jwtToken , "Bearer");
    }
}
