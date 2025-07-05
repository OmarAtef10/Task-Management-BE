package org.example.task_management.controllers;


import org.example.task_management.DTO.TaskDTO;
import org.example.task_management.models.Task;
import org.example.task_management.services.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Validated

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDto,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        Task task = taskService.createTask(taskDto.toEntity(), token);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable long id,
                                              @Valid @RequestBody TaskDTO taskDto,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authorizationHeader.substring(7);

        taskDto.setId(id);
        Task task = taskService.updateTask(taskDto.toEntity(), token, id);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authorizationHeader.substring(7);

        taskService.deleteTask(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Optional<List<TaskDTO>> listTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Optional.empty(); // Unauthorized
        }
        String token = authorizationHeader.substring(7);

        Optional<List<Task>> tasks = taskService.listTasks(token);
        return tasks.map(taskList -> taskList.stream()
                .map(TaskDTO::fromEntity)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable long id,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        String token = authorizationHeader.substring(7);

        Task task = taskService.getTaskById(token, id);
        return ResponseEntity.ok(TaskDTO.fromEntity(task));
    }
}