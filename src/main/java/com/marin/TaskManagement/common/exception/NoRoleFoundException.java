package com.marin.TaskManagement.common.exception;

/**
 * Exception for when trying to retrieve a Role by any reference and it not being found.
 */
public class NoRoleFoundException extends Exception{

    public NoRoleFoundException(String message){
        super(message);
    }
}
