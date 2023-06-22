package com.example.cs348project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

class Track_ArtistsId {
	String track_id;
	String artist;
}

@Getter
@Setter
@Entity
@Table(name="Track_Artists")
@IdClass(Track_ArtistsId.class)
public class TrackArtistEntity {

    @Id
	@Column(name = "track_id", columnDefinition = "VARCHAR(30)")
    public String track_id;

	@Id
	@Column(columnDefinition = "VARCHAR(254)")
    public String artist;

}
