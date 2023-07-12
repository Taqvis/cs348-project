package com.example.cs348project.service;

import com.example.cs348project.entity.User;
import com.example.cs348project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception("Username already used");
        }

        return userRepository.save(user);
    }

}
