package com.example.cs348project.repository;

import com.example.cs348project.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value="SELECT * FROM Users WHERE username = :username", nativeQuery = true)
    User findByUsername(@Param("username") final String username);

}
