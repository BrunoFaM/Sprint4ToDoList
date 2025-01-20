package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.models.enums.TaskStatus;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockitoBean
    private UserService userService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @MockitoBean
    private TaskRepository taskRepository;


    @MockitoBean
    private TaskService taskService;



    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void testPostTask() throws Exception {
        //Arrange
        TaskDTO taskDTO = new TaskDTO("work", "at 7 am", TaskStatus.PENDING);

        when(taskService.createTask(any(NewTask.class), anyLong())).thenReturn(taskDTO);
        when(userService.getUserById(any())).thenReturn(new UserEntity());
        when(taskService.saveTask(any(Task.class))).thenReturn(new Task());
        String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";

        String jwtToken = jwtUtils.generateToken("user@gmail.com");

        //Act - Assert
        mockMvc.perform(post("/api/user/tasks")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void testGetAllTask(){

        //Arrange
        List<TaskDTO> tasks = List.of();

        try {
            //when(taskService.getAllTasksCurrent(any())).thenReturn(tasks);
            //Act - Assert
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            MvcResult result = mockMvc.perform(get("/api/user/tasks")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(), tasks.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void testGetById(){
        //Arrange
        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            TaskDTO task = new TaskDTO("example", "example", TaskStatus.PENDING);
            when(taskService.getTaskByIdAndCurrent(any(), anyLong())).thenReturn(task);

            //Act
            MvcResult result = mockMvc.perform(get("/api/user/tasks/1")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();


        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void testDelete(){
        //Arrange

        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            //when(userService.getUserByEmail(any())).thenReturn(new UserEntity());
            //when(taskRepository.findByUserEntityAndId(any(), anyLong())).thenReturn(new Task());
            //when(taskRepository.save(any())).thenReturn(new Task());
            //doNothing().when(taskService.deleteTaskByIdAndCurrent(any(), anyLong()));

            mockMvc.perform(delete("/api/user/tasks/1")
                            .header("Authorization", "Bearer " + jwtToken)
                    )
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void testUpdate(){

        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            when(taskService.updateTaskById(any(), anyLong(), any())).thenReturn(new TaskDTO());
            String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";
            mockMvc.perform(put("/api/user/tasks/1")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
