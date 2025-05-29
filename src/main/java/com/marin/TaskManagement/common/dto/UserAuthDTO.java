package com.marin.TaskManagement.common.dto;

/**
 * Data Transfer Object for Authentication Credentials
 */
public record UserAuthDTO (
       String username,
       String password
) { }
