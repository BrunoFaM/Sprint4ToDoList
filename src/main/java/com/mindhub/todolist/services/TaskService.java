package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;

import java.util.List;

public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO createTask(TaskDTO taskDTO);

    Task saveTask(Task task);

    boolean taskExistById(Long id);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTaskById(Long id, TaskDTO taskDTO);

    Task findTaskById(Long id);

    void deleteTaskById(Long id);



}
