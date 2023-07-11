package com.example.cs348project.repository;

import com.example.cs348project.entity.User;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value ="SELECT COUNT(*) FROM Users u WHERE u.username = :name AND u.password = :password", nativeQuery = true)
    Integer countUser(@Param("name") final String name, @Param("password") final String password);

    @Query(value="SELECT username FROM Users WHERE u.username = :name", nativeQuery = true)
    String findByUsername(@Param("name") final String name);

    @Modifying
    @Query(value = "insert into Users VALUES (:name, :displayName, :password, NULL)", nativeQuery = true)
    @Transactional
    public abstract void addUser(@Param("name") String name, @Param("displayName") String displayName, String password);
    
}
