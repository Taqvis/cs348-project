package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "username", columnDefinition = "VARCHAR(36)", nullable = false)
    private String username;

    @Column(name = "display_name", columnDefinition = "VARCHAR(36)", nullable = false)
    private String displayName;

    @Column(name = "password", columnDefinition = "VARCHAR(60)", nullable = false)
    private String password;

    @Column(name = "tier", columnDefinition = "INT")
    private Integer tier;

    @JsonManagedReference(value = "playlist-owner")
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Playlist> ownedPlaylists;

    @JsonManagedReference(value = "liked-user")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlaylistLike> likedPlaylists;

}
