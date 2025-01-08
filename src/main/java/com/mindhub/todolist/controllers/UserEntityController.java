package com.mindhub.todolist.controllers;

import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// This is only for test, i have to delete this controller before to send the repo
@RestController
@RequestMapping("/api/user")
public class UserEntityController {

    @Autowired
    UserEntityRepository userEntityRepository;

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id){
        return userEntityRepository.findById(id).orElse(null);
    }
}
