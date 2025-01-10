package com.mindhub.todolist.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg){
        super(msg);
    }
}
