package com.marin.TaskManagement.common.errorhandler;

import com.marin.TaskManagement.common.exception.NoRoleFoundException;
import com.marin.TaskManagement.common.exception.NoTaskFoundException;
import com.marin.TaskManagement.common.exception.NoUserFoundException;
import com.marin.TaskManagement.task.service.TaskServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /// /////// GLOBAL ERRORS

    /**
     * Manages general unchecked exceptions that might present during the execution of the app.
     * This only prevents the app from crashing
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex , HttpServletRequest request){
        logger.error("{} \n {}", ex.getMessage(), Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something unexpected happened on the server, try again later");
    }

    /// //////// GENERAL SPRING BOOT ERRORS

    /**
     * Manages exceptions risen by trying to log in with bad credentials into the auth controller.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
    }

    /**
     * Manages exceptions risen by receive a bad body in the endpoints that require one.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex){

        return ResponseEntity.badRequest().body("Request body missing or malformed");
    }

    /**
     * Manages exceptions created by the database and mosty be risen by trying to persist duplicated entries that are restricted by unique
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        System.err.println(ex);
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }

    /**
     * Manages general errors when an element is not found, mostly coming from Repository Operations in .orElseThrow()
     * and handled here for not being specific like accessing some entity child entities.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(Exception ex , HttpServletRequest request){
        logger.error("{}" , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to fill your request because there was nothing found");
    }

    /**
     * Manages errors when trying to authenticate a user by username and it not being found.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(Exception ex, HttpServletRequest request){
        logger.error("{}" , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User with that Username was found");
    }

    /// /////// CUSTOM ERRORS

    /**
     * Manages exceptions created by trying to fetch a User and it not being found by the reference provided.
     */
    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<String> handleNoUserFoundException(Exception ex , HttpServletRequest request){
        logger.error("{}" , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Manages exceptions created by trying to fetch a Task and it not being found by the reference provided.
     */
    @ExceptionHandler(NoTaskFoundException.class)
    public ResponseEntity<String> handleNoTaskFoundException(Exception ex, HttpServletRequest request){
        logger.error("{}" , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task was found with the given parameters");
    }

    /**
     * Manages exceptions created by trying to fetch a Role and it not being found by the reference provided.
     */
    @ExceptionHandler(NoRoleFoundException.class)
    public ResponseEntity<String> handleNoRoleFoundException(Exception ex , HttpServletRequest request){
        logger.error("{}" , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find the requested role");
    }




}
