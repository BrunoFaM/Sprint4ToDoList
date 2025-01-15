package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.LoginRequest;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.UserEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService{


    UserEntity getUserById(Long id) throws UserNotFoundException;

    UserEntity getUserByEmail(String email) throws UserNotFoundException;

    UserEntityDTO getUserDTObyId(Long id) throws UserNotFoundException;

    UserEntityDTO getUser(Authentication authentication) throws UserNotFoundException;

    boolean userExistById(Long id);

    boolean userExistByEmail(String email);

    List<UserEntityDTO> getAllUsers();

    UserEntityDTO createUser(NewUser newUser);

    UserEntityDTO updateUser(Long id, NewUser newUser) throws UserNotFoundException;

    UserEntityDTO updateCurrentUser(Authentication authentication, NewUser newUser) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserNotFoundException;

    void registerUser(LoginRequest loginRequest);

    void deleteCurrentUser(Authentication authentication);


}
