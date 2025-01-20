package com.mindhub.todolist.Services;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.InvalidEmailException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.models.enums.TaskStatus;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {


    @Autowired
    private TaskService taskService;

    @MockitoBean
    private TaskRepository taskRepository;

    @MockitoBean
    private UserService userService;

    @Test
    public void testSaveTask() {
        //Arrange
        Task task = new Task("go to the gym", "tomorrow morning", TaskStatus.PENDING);
        NewTask newTask = new NewTask("go to the gym", "tomorrow morning", TaskStatus.PENDING);

        try {
            when(userService.getUserById(anyLong())).thenReturn(new UserEntity("user", "23232", "user@gmail.com"));
            when(taskRepository.save(any(Task.class))).thenReturn(task);

            //Act
            TaskDTO userCreated = taskService.createTask(newTask, 1L);


            //Assert
            Assertions.assertThat(userCreated.getTitle()).isEqualTo(newTask.getTitle());
            Assertions.assertThat(userCreated.getDescription()).isEqualTo(newTask.getDescription());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }




    }


}
