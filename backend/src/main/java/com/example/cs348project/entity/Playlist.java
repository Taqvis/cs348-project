package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PlaylistID.class)
@Table(name = "Playlists")
public class Playlist {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "playlist_name")
    private String playlistName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false)
    private User owner;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlaylistTrack> tracks;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlaylistLike> likes;

    public Playlist(String username, String playlistName) {
        this.username = username;
        this.playlistName = playlistName;
    }

}


