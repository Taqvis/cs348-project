package com.example.cs348project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.List;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Tracks")
public class TrackEntity {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "track_id", columnDefinition = "VARCHAR(30)", nullable = false)
  public String track_id;

  @Column(name = "album_name", columnDefinition = "VARCHAR(254)", nullable = false)
  public String album_name;

  @Transient
  public List<String> artists;

  @Column(name = "track_name", columnDefinition = "VARCHAR(254)", nullable = false)
  public String track_name;

  public Integer popularity;

  public Integer duration_ms;

  public Boolean explicit;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float danceability;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float energy;

  @Column(name = "music_key")
  public Integer key;

  @Column(columnDefinition = "DECIMAL(17, 15)")
  public Float loudness;

  public Integer mode;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float speechiness;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float acousticness;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float instrumentalness;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float liveness;

  @Column(columnDefinition = "DECIMAL(16, 15)")
  public Float valence;

  @Column(columnDefinition = "DECIMAL(7, 3)")
  public Float tempo;

  public Integer time_signature;

  @Column(columnDefinition = "VARCHAR(30)")
  public String track_genre;
}