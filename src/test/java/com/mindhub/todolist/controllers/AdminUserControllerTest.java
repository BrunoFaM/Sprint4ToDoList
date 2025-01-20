package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.models.enums.TaskStatus;
import com.mindhub.todolist.services.UserService;
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
public class AdminUserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    private final String username = "user@gmail.com";

    private String jwtToken;

    @BeforeEach
    void setUp(){
        jwtToken = jwtUtils.generateToken(username);
    }


    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void getAllUsers(){
        //Arrange
        List<UserEntityDTO> users = List.of();
        //when(userService.getAllUsers()).thenReturn(users);
        //Act
        try {
            mockMvc.perform(get("/api/admin/users")
                    .header("Authorization", "Bearer " + jwtToken))
                    .andExpect(status().isOk());
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
            UserEntityDTO userEntityDTO = new UserEntityDTO(new UserEntity("example", "3245", "example.com"));
            when(userService.getUserDTObyId(anyLong())).thenReturn(userEntityDTO);
            //Act
            MvcResult result = mockMvc.perform(get("/api/admin/users/1")
                    .header("Authorization", "Bearer " + jwtToken)
            ).andExpect(status().isOk()).andReturn();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testPostTask() throws Exception {

        String requestBody = "{\"username\": \"string\", \"password\": \"23245\", \"email\": \"string@gmail.com\"}";

        mockMvc.perform(post("/api/admin/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testUpdate(){

        try {
            UserEntityDTO user = new UserEntityDTO(new UserEntity());
            when(userService.updateUser(anyLong(), any(NewUser.class))).thenReturn(user);
            String requestBody = "{\"username\": \"string\", \"password\": \"23245\", \"email\": \"string@gmail.com\"}";
            mockMvc.perform(put("/api/admin/users/1")
                            .header("Authorization", "Bearer " + jwtToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @WithMockUser(username = username,  authorities = {"ADMIN"})
    public void testDeleteById(){
        //Arrange

        try {
            mockMvc.perform(delete("/api/admin/users/1")
                            .header("Authorization", "Bearer " + jwtToken)
                    )
                    .andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
