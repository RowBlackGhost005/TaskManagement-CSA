package com.marin.TaskManagement.common.exception;

/**
 * Exception for when trying to retrieve a User by any reference and not being found.
 */
public class NoUserFoundException extends Exception{

    public NoUserFoundException(String message){
        super(message);
    }
}
