package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserEntityController {

    @Autowired
    UserEntityRepository userEntityRepository;


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> users = userEntityRepository.findAll();
        List<UserEntityDTO> userEntityDTOS = users
                .stream()
                .map( user -> new UserEntityDTO(user))
                .toList();
        return new ResponseEntity<>(userEntityDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        UserEntity user = userEntityRepository.findById(id).orElse(null);
        UserEntityDTO userEntityDTO = new UserEntityDTO(user);

        return new ResponseEntity<>(userEntityDTO , HttpStatus.OK);

    }
    //validation is missing
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser){
        UserEntity user = new UserEntity(newUser.username(), newUser.password(), newUser.email());
        userEntityRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody NewUser newUser){
        UserEntity user = userEntityRepository.findById(id).orElse(null);
        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        user.setUsername(newUser.username());
        user.setEmail(newUser.email());
        user.setPassword(newUser.password());

        userEntityRepository.save(user);



        return new ResponseEntity<>(new UserEntityDTO(user), HttpStatus.OK);
    }
    // i need to delete the task that the user have firs
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        UserEntity user = userEntityRepository.findById(id).orElse(null);
        if (!userEntityRepository.existsById(id)){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        userEntityRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    


}
