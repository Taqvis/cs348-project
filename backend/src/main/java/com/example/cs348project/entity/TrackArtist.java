package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Track_Artists")
@IdClass(TrackArtistID.class)
public class TrackArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "track_id", columnDefinition = "VARCHAR(30)")
    public String trackId;

	@Id
	@Column(name = "artist", columnDefinition = "VARCHAR(254)")
    public String artist;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "track_id")
    public Track track;

}