package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dtos.LoginRequest;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.InvalidEmailException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityImpl implements UserService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity getUserById(Long id) throws UserNotFoundException {
        return userEntityRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserByEmail(String email) throws UserNotFoundException {
        return userEntityRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserEntityDTO getUserDTObyId(Long id) throws UserNotFoundException {
        return new UserEntityDTO(getUserById(id));
    }

    @Override
    public UserEntityDTO getUser(Authentication authentication) throws UserNotFoundException {
        UserEntity user = userEntityRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserEntityDTO(user);
    }

    public boolean userExistById(Long id){
        return userEntityRepository.existsById(id);
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
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
        if(!userEntityRepository.existsByEmail(newUser.email())){
            UserEntity user = new UserEntity(newUser.username(), passwordEncoder.encode(newUser.password()), newUser.email());
            userEntityRepository.save(user);
            return new UserEntityDTO(user);
        }
        throw new InvalidEmailException("email already registered");
    }

    public UserEntityDTO updateUser(Long id, NewUser newUser) throws UserNotFoundException {
        UserEntity user = getUserById(id);

        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(newUser.password());
        return new UserEntityDTO(userEntityRepository.save(user));
    }

    @Override
    public UserEntityDTO updateCurrentUser(Authentication authentication, NewUser newUser) throws UserNotFoundException {
        UserEntity user = userEntityRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(passwordEncoder.encode(newUser.password()));
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

    @Override
    public void registerUser(LoginRequest loginRequest) {
        if(userEntityRepository.existsByEmail(loginRequest.username())){
            throw new InvalidEmailException("Email already registered");
        }
    }

    public void deleteCurrentUser(Authentication authentication){
        UserEntity user = userEntityRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        for(Task task : user.getTasks()){
            taskRepository.deleteById(task.getId());
        }
        userEntityRepository.delete(user);
    }

}
