package org.example.task_management.controllers;

import org.example.task_management.DTO.TaskDTO;
import org.example.task_management.models.Task;
import org.example.task_management.services.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDto ,
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

}