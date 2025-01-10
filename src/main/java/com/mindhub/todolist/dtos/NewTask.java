package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewTask {

    @NotBlank(message = "Can't be blank")
    private String title;
    @NotBlank(message = "Can't be blank")
    private String description;
    @NotNull(message = "Can't be null")
    private TaskStatus status;

    public NewTask(){

    }


    public NewTask(String title, String description, TaskStatus taskStatus){
        this.title = title;
        this.description = description;
        this.status = taskStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
