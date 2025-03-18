package com.emil.mealmonitor.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emil.mealmonitor.model.entity.User;

import com.emil.mealmonitor.repository.UserRepository;
import com.emil.mealmonitor.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(1L, "Emil Bagirov", "emil_bgr@mail.ru", 23, 70.0, 183.0, null, 0.0);
    }

    @Test
    void saveUser_ShouldSaveUser() {

        when(userRepository.save(testUser)).thenReturn(testUser);


        userService.saveUser(testUser);


        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void getUser_ShouldReturnUser_WhenUserExists() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));


        User foundUser = userService.getUser(1L);


        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getName(), foundUser.getName());
    }

    @Test
    void getUser_ShouldThrowException_WhenUserNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(1L);
        });

        assertEquals("User with ID 1 not found", exception.getMessage());
    }
}
