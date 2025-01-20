package com.mindhub.todolist.controllers;

import com.mindhub.todolist.config.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserEntityControllerTest {

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

    

}
