package com.marin.TaskManagement.task.repository;

import com.marin.TaskManagement.common.dto.TaskDTO;
import com.marin.TaskManagement.common.dto.TaskInfoDTO;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marin.TaskManagement.common.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task , Integer> {

    @Query("SELECT new com.marin.TaskManagement.common.dto.TaskInfoDTO(t.id , t.title , t.description , t.status , t.priority , t.dueDate) " +
            "FROM Task t WHERE t.user.id = :id")
    Optional<List<TaskInfoDTO>> fetchTasksByUserId(@Param("id") int id);

    @Query("SELECT new com.marin.TaskManagement.common.dto.TaskDTO(t.id , new com.marin.TaskManagement.common.dto.UserDTO(u.id , u.username), t.title , t.description , t.status , t.priority , t.dueDate) " +
            "FROM Task t JOIN t.user u")
    Optional<List<TaskDTO>> fetchAllTasks();

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    Optional<Task> fetchTaskByIdUserId(@Param("taskId") int taskId , @Param("userId") int userId);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Optional<Task> fetchTaskWithUser(@Param("taskId") int taskId);
}
