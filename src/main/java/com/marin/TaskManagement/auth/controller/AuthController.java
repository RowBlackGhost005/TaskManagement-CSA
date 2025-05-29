package com.marin.TaskManagement.auth.controller;

import com.marin.TaskManagement.common.dto.LoginResponseDTO;
import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.auth.service.AuthService;
import com.marin.TaskManagement.user.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRequest(@RequestBody UserAuthDTO userAuth){
        try{
            User user = userService.registerUser(userAuth);

            return ResponseEntity.ok("User registered successfully");

        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginRequest(@RequestBody UserAuthDTO userAuth) throws NoUserFoundException {
        LoginResponseDTO loginResponse = authService.authUser(userAuth);

        return ResponseEntity.ok(loginResponse);
    }
}
