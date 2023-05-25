package com.example.cs348project.controller;

import com.example.cs348project.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping(path = "/hello")
    public String helloWorld() {
        return musicService.helloWorld();
    }

}
