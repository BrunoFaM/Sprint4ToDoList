package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testUserRegister(){

        //Arrange
        when(userService.createUser(any())).thenReturn(new UserEntityDTO(new UserEntity()));
        String requestBody = "{\"username\": \"any\", \"password\": \"2343245\", \"email\": \"string@gmail.com\"}";
        //Act - Assert
        try {
            MvcResult result = mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
            ).andExpect(status().isCreated()).andReturn();
            Assertions.assertThat("User registered successfully").isEqualTo(result.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    @WithMockUser(username = "user@gmail.com",  authorities = {"USER"})
    public void  testUserLogin(){

        //Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        String requestBody = "{\"username\": \"user@gmail.com\", \"password\": \"2343245\"}";
        //Act - Assert
        try {
            MvcResult result = mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)).andExpect(status().isOk()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
            Assertions.assertThat(result.getResponse().getContentAsString()).isNotBlank();
            Assertions.assertThat(result.getResponse().getContentAsString()).isNotNull();
            Assertions.assertThat(result.getResponse().getContentAsString()).isGreaterThan("32");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
