package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.services.TaskService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/users")
public class UserEntityController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping()
    @Operation(summary = "get current user info")
    public ResponseEntity<?> getUserInfo(Authentication authentication) throws UserNotFoundException {
        UserEntityDTO user = userService.getUser(authentication);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //validation is missing
    @PutMapping()
    @Operation(summary = "Update current user info", description = "Return the update info")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Updated"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody @Schema(exampleClasses = NewUser.class) @Valid NewUser newUser) throws UserNotFoundException {

        UserEntityDTO user = userService.updateCurrentUser(authentication, newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping()
    @Operation(summary = "Delete a current user", description = "Delete the current user and their tasks")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid data.")
            }
    )
    public ResponseEntity<?> deleteUserById(Authentication authentication) throws UserNotFoundException {
        userService.deleteCurrentUser(authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
