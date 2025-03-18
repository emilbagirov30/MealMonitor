package com.emil.mealmonitor.controller;

import com.emil.mealmonitor.model.entity.User;
import com.emil.mealmonitor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        var user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }
}
