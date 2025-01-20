package com.mindhub.todolist.Services;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.exceptions.InvalidEmailException;
import com.mindhub.todolist.exceptions.UserNotFoundException;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @MockitoBean
    private UserEntityRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testSaveUser() {
        //Arrange
        UserEntity user = new UserEntity("string", "12345", "string@gmail.com");
        NewUser newUser = new NewUser("string", "12345", "string@gmail.com");
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        //Act
        UserEntityDTO userCreated = userService.createUser(newUser);


        //Assert
        Assertions.assertThat(userCreated.getEmail()).isEqualTo("string@gmail.com");
        Assertions.assertThat(userCreated.getUsername()).isEqualTo("string");

    }

    @Test
    public void testFindUserById() {
        //Arrange
        UserEntity user = new UserEntity("string", "12345", "string@gmail.com");
        NewUser newUser = new NewUser("string", "12345", "string@gmail.com");
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //Act

        UserEntityDTO userCreated = userService.createUser(newUser);

        UserEntityDTO foundUser;

        try {
            foundUser = userService.getUserDTObyId(userCreated.getId());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Assert
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    public void testCreateUserInvalidEmailException(){
        //Arrange
        NewUser newUser = new NewUser("username", "password", "email@example.com");
        when(userRepository.existsByEmail(newUser.email())).thenReturn(true);

        //Act - Assert
        Assertions.assertThatThrownBy(() -> userService.createUser(newUser))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("email already registered");
    }

}