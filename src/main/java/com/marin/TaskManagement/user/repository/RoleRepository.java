package com.marin.TaskManagement.user.repository;

import com.marin.TaskManagement.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface for interacting with the persistence of Roles
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
