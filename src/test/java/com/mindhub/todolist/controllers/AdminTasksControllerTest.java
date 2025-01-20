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
    private TaskService taskService;

    private final String username = "user@gmail.com";

    private String jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = jwtUtils.generateToken(username);
    }


    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testPostTask() throws Exception {
        //Arrange
        String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";

        //Act - Assert
        mockMvc.perform(post("/api/admin/tasks/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testGetAllTask(){

        //Arrange
        List<TaskDTO> tasks = List.of();

        try {
            //Act - Assert
            MvcResult result = mockMvc.perform(get("/api/admin/tasks")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(), tasks.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testGetById(){
        //Arrange
        try {
            TaskDTO task = new TaskDTO("example", "example", TaskStatus.PENDING);
            when(taskService.getTaskById(anyLong())).thenReturn(task);

            //Act
            MvcResult result = mockMvc.perform(get("/api/admin/tasks")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testDeleteById(){
        //Arrange

        try {
            mockMvc.perform(delete("/api/admin/tasks/1")
                            .header("Authorization", "Bearer " + jwtToken)
                    )
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testUpdate(){

        try {
            //Arange
            when(taskService.updateTaskById(any(), anyLong(), any())).thenReturn(new TaskDTO());
            String requestBody = "{\"title\": \"work\", \"description\": \"at 7 am\", \"status\": \"PENDING\"}";
            //Act - Assert
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
