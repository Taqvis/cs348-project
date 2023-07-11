package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@IdClass(PlaylistLikeID.class)
@Table(name = "Playlist_Likes")
public class PlaylistLike {
    @Id
    @Column(name = "owner_username")
    private String ownerUsername;

    @Id
    @Column(name = "playlist_name")
    private String playlistName;

    @Id
    @Column(name = "liked_username")
    private String likedUsername;

    @JsonBackReference(value = "liked-user")
    @ManyToOne
    @JoinColumn(name = "liked_username", referencedColumnName = "username", insertable = false)
    private User user;

    @JsonBackReference(value = "playlist-like")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "owner_username", referencedColumnName = "username", insertable = false),
            @JoinColumn(name = "playlist_name", referencedColumnName = "playlist_name", insertable = false)})
    private Playlist playlist;

    public PlaylistLike(String ownerUsername, String playlistName, String likedUsername) {
        this.ownerUsername = ownerUsername;
        this.playlistName = playlistName;
        this.likedUsername = likedUsername;
    }

}

