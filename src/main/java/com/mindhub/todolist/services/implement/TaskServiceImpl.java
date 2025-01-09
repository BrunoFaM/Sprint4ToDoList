package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

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
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(),taskDTO.getStatus());
        return new TaskDTO(saveTask(task));
    }

    public TaskDTO getTaskById(Long id){

        return new TaskDTO(findTaskById(id));
    }

    public Task findTaskById(Long id){
        Task task = taskRepository.findById(id).orElse(null);
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

    public TaskDTO updateTaskById(Long id, TaskDTO taskDTO){
        Task task = findTaskById(id);
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setTaskStatus(taskDTO.getStatus());
        saveTask(task);
        return new TaskDTO(task);

    }
}
