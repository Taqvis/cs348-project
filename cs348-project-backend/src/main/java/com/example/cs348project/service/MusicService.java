package com.example.cs348project.service;

import com.example.cs348project.entity.ExampleEntity;
import com.example.cs348project.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    @Autowired
    private ExampleRepository exampleRepository;

    public String helloWorld() {
        return "Hello World!";
    }

    public String createExampleEntity() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setName("Example name");
        exampleRepository.save(exampleEntity);
        return "Saved";
    }

}
