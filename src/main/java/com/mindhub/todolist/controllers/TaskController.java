package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.exceptions.TaskNotFoundException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get a list of tasks", description = "Return a list of tasks or a empty one")
    public ResponseEntity<?> getAllTasks(){
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @PostMapping("/{id}")
    @Operation(summary = "Create a task", description = "Return the new task")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> createTask(@PathVariable @Parameter(description = "User Id") Long id, @RequestBody @Schema(exampleClasses = NewTask.class) @Valid NewTask taskDTO) throws UserNotFoundException {

        TaskDTO task = taskService.createTask(taskDTO, id);

        return new ResponseEntity<>(task, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by id", description = "Return a task if it exists")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> getTaskById(@PathVariable @Parameter(description = "Task Id") Long id) throws TaskNotFoundException {

        TaskDTO task = taskService.getTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Return the update task if it exist")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Updated"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> updateTaskById(@PathVariable @Parameter(description = "Task id") Long id,  @RequestBody @Schema(exampleClasses = NewTask.class) @Valid NewTask taskDTO) throws TaskNotFoundException {

        TaskDTO task = taskService.updateTaskById(id, taskDTO);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Delete the task if it exist")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> deleteTaskById(@PathVariable @Parameter(description = "Task id") Long id){

        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
