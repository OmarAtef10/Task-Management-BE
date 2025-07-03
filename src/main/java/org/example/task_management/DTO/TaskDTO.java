package org.example.task_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.task_management.models.Priority;
import org.example.task_management.models.Task;
import org.example.task_management.models.User;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private long id;
    @NotNull(message = "Title cannot be null")
    private String title;
    private String description;
    @NotNull(message = "Priority cannot be null")
    private Priority priority;
    private Date createdAt;
    private Date updatedAt;
    private Date dueDate;
    private String createdBy;
    private String assignedUser;


    public Task toEntity() {
        Task task = new Task();
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setPriority(this.priority);
        task.setCreatedAt(this.createdAt);
        task.setUpdatedAt(this.updatedAt);
        task.setDueDate(this.dueDate);
        if (this.assignedUser != null && !this.assignedUser.isEmpty()) {
            User user = new User();
            user.setUsername(this.assignedUser);
            task.setAssignedUser(user);
        }

        return task;
    }

    public static TaskDTO fromEntity(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedBy(task.getUser().getUsername());
        if (task.getAssignedUser() != null) {
            dto.setAssignedUser(task.getAssignedUser().getUsername());
        }
        return dto;
    }

}
