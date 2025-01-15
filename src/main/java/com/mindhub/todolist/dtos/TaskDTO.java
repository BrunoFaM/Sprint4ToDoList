package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskDTO {

    private Long id;
    @NotBlank(message = "Can't be blank")
    private String title;
    @NotBlank(message = "Can't be blank")
    private String description;
    @NotNull(message = "Can't be null")
    private TaskStatus status;

    public TaskDTO(){

    }


    public TaskDTO(String title, String description, TaskStatus taskStatus){
        this.title = title;
        this.description = description;
        this.status = taskStatus;
    }

    public TaskDTO(Task task){
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        status = task.getTaskStatus();
    }

    public Long getId() {
        return id;
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
