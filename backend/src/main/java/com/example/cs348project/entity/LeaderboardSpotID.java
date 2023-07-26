package com.example.cs348project.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardSpotID implements Serializable {
    private String ownerUsername;
    private Integer likes;
    private Integer position;
}