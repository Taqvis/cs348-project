package com.example.cs348project.repository;

import com.example.cs348project.entity.ExampleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExampleRepository extends CrudRepository<ExampleEntity, Integer> {

    // Example using SQL instead (Setting nativeQuery = false (default will use JPQL instead)
    @Query(value = "SELECT * FROM ExampleEntity WHERE name = 'Example name'", nativeQuery = true)
    List<ExampleEntity> getBasedOnQuery();

}
