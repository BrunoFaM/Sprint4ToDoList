package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;

public interface UserService{


    UserEntity getUserById(Long id);

    UserEntityDTO getUserDTObyId(Long id);

    boolean userExistById(Long id);

    List<UserEntityDTO> getAllUsers();

    UserEntityDTO createUser(NewUser newUser);

    UserEntityDTO updateUser(Long id, NewUser newUser);

}
