package com.marin.TaskManagement.common.exception;

/**
 * Exception for when trying to retrieve a Task by any reference and it not being found
 */
public class NoTaskFoundException extends Exception{

    public NoTaskFoundException(String message){
        super(message);
    }
}
