package com.example.cs348project.repository;

import com.example.cs348project.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, String> {

    @Query(value = "SELECT * FROM Tracks WHERE track_name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<Track> findByName(@Param("name") final String name);

    @Query(value =
            "WITH liked_avg(avgVal) AS " +
                    "(SELECT AVG(danceability)" +
                    "FROM Tracks t, LikedTracks lt " +
                    "WHERE t.track_id = lt.track_id AND lt.liked_username = :userId) " +
                    "SELECT t.track_id track_id " +
                    "FROM Tracks t, liked_avg " +
                    "WHERE ABS(t.danceability - liked_avg.avgVal) <= 0.005 LIMIT 20;", nativeQuery = true)
    List<String> getDanceabilityRecommendations(String userId);

    @Query(value =
            "WITH liked_avg(avgVal) AS " +
                    "(SELECT AVG(energy)" +
                    "FROM Tracks t, LikedTracks lt " +
                    "WHERE t.track_id = lt.track_id AND lt.liked_username = :userId) " +
                    "SELECT t.track_id track_id " +
                    "FROM Tracks t, liked_avg " +
                    "WHERE ABS(t.energy - liked_avg.avgVal) <= 0.005 LIMIT 20;", nativeQuery = true)
    List<String> getEnergyRecommendations(String userId);

    @Query(value =
            "WITH liked_avg(avgVal) AS " +
                    "(SELECT AVG(tempo)" +
                    "FROM Tracks t, LikedTracks lt " +
                    "WHERE t.track_id = lt.track_id AND lt.liked_username = :userId) " +
                    "SELECT t.track_id track_id " +
                    "FROM Tracks t, liked_avg " +
                    "WHERE ABS(t.tempo - liked_avg.avgVal) <= 2 LIMIT 20;", nativeQuery = true)
    List<String> getTempoRecommendations(String userId);
}
