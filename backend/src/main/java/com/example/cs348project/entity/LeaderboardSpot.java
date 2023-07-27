package com.example.cs348project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(LeaderboardSpotID.class)
@Table(name = "leaderboard")
public class LeaderboardSpot {
    @Id
    @Column(name = "owner_username")
    private String ownerUsername;

    @Id
    @Column(name = "likes")
    private Integer likes;

    @Id
    @Column(name = "position")
    private Integer position;

    public LeaderboardSpot(String ownerUsername, Integer likes, Integer position) {
        this.ownerUsername = ownerUsername;
        this.likes = likes;
        this.position = position;
    }

}
