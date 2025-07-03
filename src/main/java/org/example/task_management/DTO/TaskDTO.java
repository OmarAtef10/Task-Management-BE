package org.example.task_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.task_management.models.Priority;
import org.example.task_management.models.Task;
import org.example.task_management.models.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private Long userId;
    private long assignedUserId;


    public Task toEntity() {
        Task task = new Task();
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setPriority(this.priority);
        task.setCreatedAt(this.createdAt);
        task.setUpdatedAt(this.updatedAt);
        task.setDueDate(this.dueDate);
        if(  this.assignedUserId > 0) {
            User assignedUser = new User();

            assignedUser.setId(this.assignedUserId);
            task.setAssignedUser(assignedUser);

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
        dto.setUserId(task.getUser().getId());
        if (task.getAssignedUser() != null) {

            dto.setAssignedUserId(task.getAssignedUser().getId());

        }
        return dto;
    }

}
