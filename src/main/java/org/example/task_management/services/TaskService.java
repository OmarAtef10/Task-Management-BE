package org.example.task_management.services;

import org.example.task_management.models.Task;
import org.example.task_management.models.User;
import org.example.task_management.repositories.TaskRepository;
import org.example.task_management.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                task.setAssignedUser(userRepository.findUsersByUsername(task.getAssignedUser().getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("Assigned user not found")));
            } catch (NullPointerException e) {
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

            try {
                existingTask.setAssignedUser(userRepository.findUsersByUsername(task.getAssignedUser().getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("Assigned user not found")));
            } catch (NullPointerException e) {
                existingTask.setAssignedUser(null);

            }
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setPriority(task.getPriority());
            existingTask.setDueDate(task.getDueDate());
            return taskRepository.save(existingTask);
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }

    public void deleteTask(String token, long taskId) {
        String username = jwtService.extractUsername(token);
        Long userId = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();

        if (userRepository.findUsersByUsername(username).isPresent()) {
            Task existingTask = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));
            if (!Objects.equals(existingTask.getUser().getId(), userId)) {
                throw new IllegalArgumentException("You can only delete your own tasks");
            }
            taskRepository.delete(existingTask);
        } else {
            throw new IllegalArgumentException("User does not exist");
        }

    }

    public Optional<List<Task>> listTasks(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user != null) {
            return taskRepository.findTasksByUserId(user.getId());
        } else {
            throw new IllegalArgumentException("User does not exist");
        }
    }
}
