package com.example.cs348project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Tracks")
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "track_id", columnDefinition = "VARCHAR(30)", nullable = false)
  public String trackId;

  @Column(name = "album_name", columnDefinition = "VARCHAR(254)", nullable = false)
  public String albumName;

  @Column(name = "track_name", columnDefinition = "VARCHAR(254)", nullable = false)
  public String trackName;

  @Column(name = "popularity")
  public Integer popularity;

  @Column(name = "duration_ms")
  public Integer duration;

  @Column(name = "explicit")
  public Boolean explicit;

  @Column(name = "danceability", columnDefinition = "DECIMAL(16, 15)")
  public Float danceability;

  @Column(name = "energy", columnDefinition = "DECIMAL(16, 15)")
  public Float energy;

  @Column(name = "music_key")
  public Integer key;

  @Column(name = "loudness", columnDefinition = "DECIMAL(17, 15)")
  public Float loudness;

  @Column(name = "mode")
  public Integer mode;

  @Column(name = "speechiness", columnDefinition = "DECIMAL(16, 15)")
  public Float speechiness;

  @Column(name = "acousticness", columnDefinition = "DECIMAL(16, 15)")
  public Float acousticness;

  @Column(name = "instrumentalness", columnDefinition = "DECIMAL(16, 15)")
  public Float instrumentalness;

  @Column(name = "liveness", columnDefinition = "DECIMAL(16, 15)")
  public Float liveness;

  @Column(name = "valence", columnDefinition = "DECIMAL(16, 15)")
  public Float valence;

  @Column(name = "tempo", columnDefinition = "DECIMAL(7, 3)")
  public Float tempo;

  @Column(name = "time_signature")
  public Integer timeSignature;

  @Column(name = "track_genre", columnDefinition = "VARCHAR(30)")
  public String trackGenre;

  @OneToMany(mappedBy = "track", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public List<TrackArtist> artists;

  @JsonIgnore
  @OneToMany(mappedBy = "track", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public List<PlaylistTrack> playlistTracks;
}