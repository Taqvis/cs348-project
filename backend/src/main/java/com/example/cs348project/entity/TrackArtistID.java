package com.example.cs348project.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
class TrackArtistID implements Serializable {
    public String trackId;
    public String artist;
}
