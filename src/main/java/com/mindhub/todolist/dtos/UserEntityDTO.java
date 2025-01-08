package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;

public class UserEntityDTO {

    private Long id;

    private String username, email;

    private List<TaskDTO> tasks;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
