package com.marin.TaskManagement.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marin.TaskManagement.common.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task , Integer> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :id")
    Optional<List<Task>> fetchTaskByUserId(@Param("id") int id);

    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    Optional<Task> fetchTaskByIdUserId(@Param("taskId") int taskId , @Param("userId") int userId);
}
