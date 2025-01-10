package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityImpl implements UserService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public UserEntity getUserById(Long id) throws UserNotFoundException {
        return userEntityRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntityDTO getUserDTObyId(Long id) throws UserNotFoundException {
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

    public UserEntityDTO updateUser(Long id, NewUser newUser) throws UserNotFoundException {
        UserEntity user = getUserById(id);

        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(newUser.password());
        return new UserEntityDTO(userEntityRepository.save(user));
    }

    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        UserEntity user = getUserById(id);
        for(Task task : user.getTasks()){
            taskRepository.deleteById(task.getId());
        }
        userEntityRepository.deleteById(id);
    }

}
