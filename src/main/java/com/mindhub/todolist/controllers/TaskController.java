package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllTasks(){
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // I can pass the id for a path variable
    // i can extract more logic from here
    // user not enlaced
    @PostMapping("/{id}")
    public ResponseEntity<?> createTask(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
        //UserEntity user = userService.findUserById(id);
        if (!userService.userExistById(id)){
            return new ResponseEntity<>("User not find", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskService.createTask(taskDTO), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){

        if (!taskService.taskExistById(id)){
            return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
        }

        TaskDTO task = taskService.getTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }
    // only works with all the attributes
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        if(!taskService.taskExistById(id)){
            return new ResponseEntity<>("Task not found", HttpStatus.BAD_REQUEST);
        }

        TaskDTO task = taskService.updateTaskById(id, taskDTO);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id){
        if (!taskService.taskExistById(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.GONE);

    }

}
