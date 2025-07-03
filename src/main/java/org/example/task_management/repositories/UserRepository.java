package org.example.task_management.repositories;

import org.example.task_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUsersByUsername(String username);
}
