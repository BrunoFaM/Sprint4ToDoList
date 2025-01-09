package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping
    public ResponseEntity<?> getAllTasks(){

        List<TaskDTO> tasks = taskRepository
                .findAll()
                .stream()
                .map( task -> new TaskDTO(task))
                .toList();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // I can pass the id for a path variable
    @PostMapping("/{id}")
    public ResponseEntity<?> createTask(@PathVariable Long id,@RequestBody TaskDTO taskDTO){
        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(),taskDTO.getStatus());
        UserEntity user = userEntityRepository.findById(id).orElse(null);
        if (user == null){
            return new ResponseEntity<>("User not find", HttpStatus.BAD_REQUEST);
        }
        user.addTask(task);
        taskRepository.save(task);
        return new ResponseEntity<>(new TaskDTO(task), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){

        if (!taskRepository.existsById(id)){
            return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
        }

        Task task = taskRepository.findById(id).orElse(null);

        return new ResponseEntity<>(new TaskDTO(task), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null){
            return new ResponseEntity<>("Task not found", HttpStatus.BAD_REQUEST);
        }
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setTaskStatus(taskDTO.getStatus());

        taskRepository.save(task);

        return new ResponseEntity<>(new TaskDTO(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id){
        if (!taskRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.GONE);

    }

}
