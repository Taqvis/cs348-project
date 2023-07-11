package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Tracks")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "username", columnDefinition = "VARCHAR(36)", nullable = false)
    public String username;

    @Column(name = "display_name", columnDefinition = "VARCHAR(36)", nullable = false)
    public String displayName;

    @Column(name = "password", columnDefinition = "VARCHAR(20)", nullable = false)
    public String password;

    @Column(name = "rank", columnDefinition = "INT")
    public String rank;

    public String getUsername() {
        return username;
    }


}
