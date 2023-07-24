package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(PlaylistTrackID.class)
@Table(name = "Playlist_Tracks")
public class PlaylistTrack {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "playlist_name")
    private String playlistName;

    @Id
    @Column(name = "track_id")
    private String trackId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "track_id", referencedColumnName = "track_id", insertable = false)
    private Track track;

    @JsonBackReference
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "username", referencedColumnName = "username", insertable = false),
        @JoinColumn(name = "playlist_name", referencedColumnName = "playlist_name", insertable = false)})
    private Playlist playlist;

    public PlaylistTrack(String username, String playlistName, String trackId) {
        this.username = username;
        this.playlistName = playlistName;
        this.trackId = trackId;
    }

}

