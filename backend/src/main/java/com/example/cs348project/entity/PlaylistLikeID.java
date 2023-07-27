package com.example.cs348project.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlaylistLikeID implements Serializable {
    private String ownerUsername;
    private String playlistName;
    private String likedUsername;
}
