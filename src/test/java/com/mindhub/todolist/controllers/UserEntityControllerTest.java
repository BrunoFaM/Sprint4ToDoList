package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.models.enums.TaskStatus;
import com.mindhub.todolist.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockitoBean
    private UserService userService;

    private final String username = "user@gmail.com";

    private String jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = jwtUtils.generateToken(username);
    }

    @Test
    @WithMockUser(username = username,  authorities = {"USER"})
    public void testGetCurrentUser(){

        //Arrange


        try {

            when(userService.getUser(any())).thenReturn(new UserEntityDTO(new UserEntity()));
            mockMvc.perform(get("/api/user/users")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"USER"})
    public void testUpdateCurrentUser(){

        try {
            //Arrange
            when(userService.updateCurrentUser(any(), any())).thenReturn(new UserEntityDTO(new UserEntity()));
            String requestBody = "{\"username\": \"string\", \"password\": \"23245\", \"email\": \"string@gmail.com\"}";

            //Act - Assert
            mockMvc.perform(put("/api/user/users")
                    .header("Authorization", "Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            ).andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"USER"})
    public void testDeleteCurrentUser(){

        try {

            //Act - Assert
            mockMvc.perform(delete("/api/user/users")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
