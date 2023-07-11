package com.example.cs348project.service;

import com.example.cs348project.entity.Track;
import com.example.cs348project.entity.User;
import com.example.cs348project.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUserEntity() {
        User userEntity = new User();
        userEntity.setUsername("Example name");
        userRepository.save(userEntity);
        return "Saved";
    }

    public Integer countUser(final String username, final String password) {
        return userRepository.countUser(username, password);
    }

    public String findbyUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public void addUser(final String username, final String displayName, final String password) {
        userRepository.addUser(username, displayName, password);
    }
}
