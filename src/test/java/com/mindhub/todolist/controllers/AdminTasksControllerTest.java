package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtAuthenticationFilter;
import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.config.SecurityConfig;
import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.models.enums.Role;
import com.mindhub.todolist.models.enums.TaskStatus;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.context.*;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminTasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockitoBean
    private UserService userService;

    @Autowired
    private UserEntityRepository userEntityRepository;


    @MockitoBean
    private TaskService taskService;

//    private UserEntity user;

//    @BeforeEach
//    void setUp(){
//        user = new UserEntity("string", "2334" ,"string@gmail.com");
//        user.setRole(Role.ADMIN);
//        user = userEntityRepository.save(user);
        //userService.createUser(new NewUser("example", "1234", "user@gmail.com"));

    //}

//    @Test
//    //@WithMockUser(username = "user", roles = {"USER"})
//    public void testPostTask() throws Exception {
//        NewTask newTask = new NewTask("work", "at 7 am", TaskStatus.PENDING);
//        TaskDTO taskDTO = new TaskDTO("work", "at 7 am", TaskStatus.PENDING);
//
//        //given(taskService.createTask(ArgumentMatchers.any(), anyLong())).willAnswer(invocationOnMock -> invocationOnMock.getArguments());
//        when(taskService.createTask(any(NewTask.class), anyLong())).thenReturn(taskDTO);
//        String requestBody = "{\"title\" : \"work\", \"description\" : \"at 7 am\", \"status\" : \"PENDING\"}";
//
//        String jwtToken = jwtUtils.generateToken("string@gmail.com");
//        mockMvc.perform(post("/api/admin/task/1")
//                        .header("Authorization", "Bearer " + jwtToken)
//                        .queryParam("id", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                        .andExpect(status().isCreated());
//
//    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"ADMIN"})
    public void testPostTask() throws Exception {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(aut);
        //System.out.println(user.getId());
        NewTask newTask = new NewTask("work", "at 7 am", TaskStatus.PENDING);
        TaskDTO taskDTO = new TaskDTO("work", "at 7 am", TaskStatus.PENDING);


        when(taskService.createTask(any(NewTask.class), anyLong())).thenReturn(taskDTO);
        when(userService.getUserById(any())).thenReturn(new UserEntity());
        when(taskService.saveTask(any(Task.class))).thenReturn(new Task());
        String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";
        //UserEntityDTO userEntityDTO =userService.getUserDTObyId(1L);
        //System.out.println(userEntityDTO);

        String jwtToken = jwtUtils.generateToken("user@gmail.com");
        mockMvc.perform(post("/api/admin/tasks/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"ADMIN"})
    public void testGetAllTask(){

        //Arrange
        List<TaskDTO> tasks = List.of();

        try {
            when(taskService.getAllTasks()).thenReturn(tasks);
            //Act - Assert
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            MvcResult result = mockMvc.perform(get("/api/admin/tasks")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(), tasks.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"ADMIN"})
    public void testGetById(){
        //Arrange
        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            TaskDTO task = new TaskDTO("example", "example", TaskStatus.PENDING);
            when(taskService.getTaskById(anyLong())).thenReturn(task);

            //Act
            MvcResult result = mockMvc.perform(get("/api/admin/tasks")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();


        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"ADMIN"})
    public void testDeleteById(){
        //Arrange

        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            mockMvc.perform(delete("/api/admin/tasks/1")
                            .header("Authorization", "Bearer " + jwtToken)
                    )
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"ADMIN"})
    public void testUpdate(){

        try {
            String jwtToken = jwtUtils.generateToken("user@gmail.com");
            when(taskService.updateTaskById(any(), anyLong(), any())).thenReturn(new TaskDTO());
            String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";
            mockMvc.perform(put("/api/admin/tasks/1")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
