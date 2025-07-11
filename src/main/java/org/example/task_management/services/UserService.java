package org.example.task_management.services;

import org.example.task_management.models.User;
import org.example.task_management.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
