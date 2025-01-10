package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.exceptions.TaskNotFoundException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<TaskDTO> getAllTasks() {
        List<TaskDTO> tasks = taskRepository
                .findAll()
                .stream()
                .map( task -> new TaskDTO(task))
                .toList();
        return tasks;
    }


    @Override
    public TaskDTO createTask(NewTask taskDTO, Long id) throws UserNotFoundException {
        UserEntity user = userService.getUserById(id);

        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(),taskDTO.getStatus());
        user.addTask(task);
        return new TaskDTO(saveTask(task));
    }

    public TaskDTO getTaskById(Long id) throws TaskNotFoundException {

        return new TaskDTO(findTaskById(id));
    }

    public Task findTaskById(Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return task;
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean taskExistById(Long id) {
        return taskRepository.existsById(id);
    }

    public TaskDTO updateTaskById(Long id, NewTask taskDTO) throws TaskNotFoundException {
        Task task = findTaskById(id);
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setTaskStatus(taskDTO.getStatus());
        saveTask(task);
        return new TaskDTO(task);

    }
}
