package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.exceptions.TaskNotFoundException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;

import java.util.List;

public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO createTask(NewTask taskDTO, Long id) throws UserNotFoundException;

    Task saveTask(Task task);

    boolean taskExistById(Long id);

    TaskDTO getTaskById(Long id) throws TaskNotFoundException;

    TaskDTO updateTaskById(Long id, NewTask taskDTO) throws TaskNotFoundException;

    Task findTaskById(Long id) throws TaskNotFoundException;

    void deleteTaskById(Long id);



}
