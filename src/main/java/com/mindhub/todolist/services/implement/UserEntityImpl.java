package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityImpl implements UserService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserEntity getUserById(Long id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntityDTO getUserDTObyId(Long id) {
        return new UserEntityDTO(getUserById(id));
    }

    public boolean userExistById(Long id){
        return userEntityRepository.existsById(id);
    }

    @Override
    public List<UserEntityDTO> getAllUsers() {
        List<UserEntity> users = userEntityRepository.findAll();
        List<UserEntityDTO> userEntityDTOS = users
                .stream()
                .map( user -> new UserEntityDTO(user))
                .toList();
        return  userEntityDTOS;
    }

    @Override
    public UserEntityDTO createUser(NewUser newUser) {
        UserEntity user = new UserEntity(newUser.username(), newUser.password(), newUser.email());
        userEntityRepository.save(user);
        return new UserEntityDTO(user);
    }

    public UserEntityDTO updateUser(Long id, NewUser newUser){
        UserEntity user = getUserById(id);

        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(newUser.password());
        return new UserEntityDTO(userEntityRepository.save(user));
    }
}
