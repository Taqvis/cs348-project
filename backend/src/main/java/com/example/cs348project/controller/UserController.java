package com.example.cs348project.controller;

import com.example.cs348project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/test/login/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String loginTest(@PathVariable final String username, @PathVariable final String password) {
        if (userService.countUser(username, password) == 1) return "success";
        return "failed";
    }

    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public int login(@RequestParam String username, @RequestParam String password) {
        // 1 if login successful and login fails otherwise
        int canLogin = userService.countUser(username, password);
        if (canLogin != 1) {
            throw new IllegalArgumentException("Invalid username or password");
        }
      return canLogin;  
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/user/create") 
    public String createAccount(@RequestParam final String username, @RequestParam final String displayName, @RequestParam final String password){
        if (username == null || displayName == null || password == null) {
            throw new IllegalArgumentException("one of the information missing : username, display name, password");
        }
        else if (8 > password.length() || password.length() > 20) {
            throw new IllegalArgumentException("password length should be between 8 and 20 inclusive");
        }
        else if (userService.findbyUsername(username) != null) {
            throw new IllegalArgumentException("the username already exists");
        }
        userService.addUser(username, displayName, password);
        return "user added";
    }

    
}
