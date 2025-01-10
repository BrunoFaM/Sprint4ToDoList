package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;

public interface UserService{


    UserEntity getUserById(Long id) throws UserNotFoundException;

    UserEntityDTO getUserDTObyId(Long id) throws UserNotFoundException;

    boolean userExistById(Long id);

    List<UserEntityDTO> getAllUsers();

    UserEntityDTO createUser(NewUser newUser);

    UserEntityDTO updateUser(Long id, NewUser newUser) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserNotFoundException;


}
