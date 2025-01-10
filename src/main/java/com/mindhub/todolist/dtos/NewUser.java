package com.mindhub.todolist.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewUser(@NotBlank(message = "Insert username") String username,@NotBlank(message = "Insert password") String password,@Email(message = "Has to be an valid email")  String email) {
}
