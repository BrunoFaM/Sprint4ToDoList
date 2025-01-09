package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;

public class UserEntityDTO {

    private final Long id;

    private final String username, email;

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
