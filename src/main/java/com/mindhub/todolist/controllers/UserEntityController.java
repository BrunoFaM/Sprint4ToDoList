package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserEntityController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<UserEntityDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){

        UserEntityDTO userEntityDTO = userService.getUserDTObyId(id);

        return new ResponseEntity<>(userEntityDTO , HttpStatus.OK);

    }
    //validation is missing
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser){

        UserEntityDTO user = userService.createUser(newUser);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody NewUser newUser){
        if (!userService.userExistById(id)){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        UserEntityDTO user = userService.updateUser(id, newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    // i need to delete the task that the user have firs
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
//
//    }

    


}
