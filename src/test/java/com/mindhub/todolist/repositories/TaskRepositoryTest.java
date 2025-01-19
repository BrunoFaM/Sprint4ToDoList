package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.enums.TaskStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask(){

        //Arrange
        Task task = new Task("Workout", "make at leat 1 hour of exercise", TaskStatus.IN_PROGRESS);

        //Act
        Task savedTask = taskRepository.save(task);

        //Assert
        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getId()).isGreaterThan(0);
        Assertions.assertThat(savedTask.getTitle()).isEqualTo("Workout");
    }

    @Test
    public void testFindSavedTaskById(){

        //Arrange
        Task task = new Task("Workout", "make at leat 1 hour of exercise", TaskStatus.IN_PROGRESS);

        //Act
        Task savedTask = taskRepository.save(task);
        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);

        //Assert
        Assertions.assertThat(foundTask).isNotNull();
        Assertions.assertThat(foundTask.getId()).isEqualTo(savedTask.getId());
        Assertions.assertThat(foundTask.getTitle()).isEqualTo(savedTask.getTitle());
    }

    @Test
    public void testDeleteSavedTask(){
        //Arrange
        Task task = new Task("Workout", "make at leat 1 hour of exercise", TaskStatus.IN_PROGRESS);

        //Act
        Task savedTask = taskRepository.save(task);
        taskRepository.deleteById(savedTask.getId());
        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);

        //Assert
        Assertions.assertThat(foundTask).isNull();
    }

    @Test
    public void testUpdateTask(){
        //Arrange
        Task task = new Task("Workout", "make at leat 1 hour of exercise", TaskStatus.IN_PROGRESS);

        //Act
        Task savedTask = taskRepository.save(task);
        savedTask.setTitle("make dinner");
        Task updatedTask = taskRepository.save(savedTask);

        //Assert
        Assertions.assertThat(savedTask.getTaskStatus().toString()).isEqualTo(updatedTask.getTaskStatus().toString());
        Assertions.assertThat(updatedTask.getTitle()).isEqualTo("make dinner");
    }



}
