package org.example.task_management.controllers;

import org.example.task_management.DTO.UserDTO;
import org.example.task_management.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {

        return userService.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();
    }
}
