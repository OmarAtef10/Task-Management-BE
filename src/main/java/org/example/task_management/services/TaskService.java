package org.example.task_management.services;

import org.example.task_management.models.Task;
import org.example.task_management.repositories.TaskRepository;
import org.example.task_management.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;

    }

    public Task createTask(Task task, String token) {
        String username = jwtService.extractUsername(token);

        if (userRepository.findUsersByUsername(username).isPresent()) {

            task.setUser(userRepository.findUsersByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found")));
           try {
               if (task.getAssignedUser().getId() > 0) {
                   task.setAssignedUser(userRepository.findById(task.getAssignedUser().getId())
                           .orElseThrow(() -> new IllegalArgumentException("Assigned user not found")));
               } else {
                   task.setAssignedUser(userRepository.findUsersByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found")));
               }
           }
           catch (NullPointerException e) {
               task.setAssignedUser(null);

           }
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }

    public Task updateTask(Task task, String token, long taskId) {
        String username = jwtService.extractUsername(token);
        Long userId = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();

        if (userRepository.findUsersByUsername(username).isPresent()) {
            Task existingTask = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));
            if (!Objects.equals(existingTask.getUser().getId(), userId)) {
                throw new IllegalArgumentException("You can only update your own tasks");
            }
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setPriority(task.getPriority());
            existingTask.setAssignedUser(task.getAssignedUser());
            existingTask.setDueDate(task.getDueDate());
            return taskRepository.save(existingTask);
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }
}
