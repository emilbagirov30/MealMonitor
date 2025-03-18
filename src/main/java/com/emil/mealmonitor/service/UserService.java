package com.emil.mealmonitor.service;

import com.emil.mealmonitor.exception.UserNotFoundException;
import com.emil.mealmonitor.model.entity.User;
import com.emil.mealmonitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void saveUser (User user){
          userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
