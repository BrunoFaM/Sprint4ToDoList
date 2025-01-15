package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUsersController {

    @Autowired
    UserService userService;

    @GetMapping
    @Operation(summary = "Get a list of users", description = "Return a list of users or a empty one")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntityDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id", description = "Return a user if it exists")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> getUserById(@PathVariable @Parameter(description = "User Id") Long id) throws UserNotFoundException {

        UserEntityDTO userEntityDTO = userService.getUserDTObyId(id);

        return new ResponseEntity<>(userEntityDTO , HttpStatus.OK);

    }

    @PostMapping
    @Operation(summary = "Create a user", description = "Return the new user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> createUser(@RequestBody @Schema(exampleClasses = NewUser.class) @Valid NewUser newUser){

        UserEntityDTO user = userService.createUser(newUser);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Return the update task if it exist")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Updated"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> updateUser(@PathVariable @Parameter(description = "User Id") Long id, @RequestBody @Schema(exampleClasses = NewUser.class) @Valid NewUser newUser) throws UserNotFoundException {

        UserEntityDTO user = userService.updateUser(id, newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user and their tasks", description = "Delete the user and all their tasks if it exists")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> deleteUserById(@PathVariable @Parameter(description = "User Id") Long id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
