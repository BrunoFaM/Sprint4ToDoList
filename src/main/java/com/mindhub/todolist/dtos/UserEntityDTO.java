package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserEntityDTO {

    private final Long id;
    @NotBlank(message = "Can't be blank")
    private final String username;
    @Email(message = "Has to be an valid email")
    private final String email;

    private final List<TaskDTO> tasks;

    public UserEntityDTO(UserEntity user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        tasks = user.getTasks()
                .stream()
                .map( task -> new TaskDTO(task))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }
}
