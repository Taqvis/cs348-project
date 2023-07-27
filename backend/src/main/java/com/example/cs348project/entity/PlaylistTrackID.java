package com.example.cs348project.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlaylistTrackID implements Serializable {
    private String username;
    private String playlistName;
    private String trackId;
}
