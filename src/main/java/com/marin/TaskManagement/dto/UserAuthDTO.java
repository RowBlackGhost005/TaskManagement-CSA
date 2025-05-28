package com.marin.TaskManagement.dto;

/**
 * Data Transfer Object for Authentication Credentials
 */
public record UserAuthDTO (
       String username,
       String password
) { }
