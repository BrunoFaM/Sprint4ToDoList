package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;

import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get al the tasks of the current user")
    public ResponseEntity<?> getAllTasksCurrent(Authentication authentication) throws UserNotFoundException {
        List<TaskDTO> tasks = taskService.getAllTasksCurrent(authentication);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by Id")
    public ResponseEntity<?> getTaskByIdAndCurrent(Authentication authentication, @PathVariable Long id) throws UserNotFoundException {
        TaskDTO task = taskService.getTaskByIdAndCurrent(authentication, id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Create a task")
    public ResponseEntity<?> createTaskCurrent(Authentication authentication, @RequestBody @Schema(exampleClasses = NewTask.class) @Valid NewTask newTask) throws UserNotFoundException {

        TaskDTO task = taskService.createTaskCurrent(authentication, newTask);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Return the update task")
    public ResponseEntity<?> updateTask(Authentication authentication, @PathVariable @Parameter(description = "Task Id") Long id,@RequestBody @Schema(exampleClasses = NewTask.class) @Valid NewTask newTask) throws UserNotFoundException {

        TaskDTO task = taskService.updateTaskById(authentication, id, newTask);
        return  new ResponseEntity<>(task, HttpStatus.OK);
    }

    //i want to make a soft delete, implement is missing
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by id", description = "Delete the task if it exist")
    public ResponseEntity<?> deleteTaskByIdCurrent(Authentication authentication, @PathVariable @Parameter(description = "Task id") Long id) throws UserNotFoundException {
        taskService.deleteTaskByIdAndCurrent(authentication, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
