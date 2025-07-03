package org.example.task_management.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.task_management.models.User;

import java.security.PublicKey;

@Data
@Setter
@Getter
public class UserDTO {
    long id;
    String username;

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        return user;
    }

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
