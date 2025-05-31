package com.marin.TaskManagement.auth.controller;

import com.marin.TaskManagement.common.dto.LoginResponseDTO;
import com.marin.TaskManagement.common.dto.UserAuthDTO;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.common.exception.NoRoleFoundException;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.auth.service.AuthService;
import com.marin.TaskManagement.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Manages login and registration of Users")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "Creates an User account in the API",
            description = "Register the Client in the API using the given credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful registration attempt",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "409", description = "User already taken")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerRequest(
            @RequestBody @Parameter(description = "User credentials to register" , required = true)
            @Schema(description = "UserAuthDTO containing user credentials to register") UserAuthDTO userAuth) throws NoRoleFoundException {
        try{
            User user = userService.registerUser(userAuth);

            return ResponseEntity.ok("Created user "+ user.getUsername() + " successfully");

        }catch(DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

    }


    @Operation(
            summary = "Request access to de API",
            description = "Attempts to authenticates the client using the given credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful login attempt",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Bad Credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginRequest(
            @RequestBody @Parameter(description = "User credentials to authenticate" , required = true)
            @Schema(description = "UserAuthDTO containing user credentials to log-in") UserAuthDTO userAuth) throws NoUserFoundException {
        LoginResponseDTO loginResponse = authService.authUser(userAuth);

        return ResponseEntity.ok(loginResponse);
    }
}
