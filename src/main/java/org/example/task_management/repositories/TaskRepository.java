package org.example.task_management.repositories;

import org.example.task_management.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findTasksByUserId(Long userId);

}
