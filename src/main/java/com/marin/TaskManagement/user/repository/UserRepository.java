package com.marin.TaskManagement.user.repository;

import com.marin.TaskManagement.common.dto.UserDTO;
import com.marin.TaskManagement.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Manages database operations of Users.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<UserDTO> findUserById(@Param("id") int id);
}
