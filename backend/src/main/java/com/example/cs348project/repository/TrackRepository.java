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
            "WITH liked_avg(avgDanceability, avgEnergy, avgTempo) AS " +
                    "(SELECT AVG(danceability) avgDanceability, AVG(energy) avgEnergy, AVG(tempo) avgTempo " +
                    "FROM Tracks t, LikedTracks lt " +
                    "WHERE t.track_id = lt.track_id AND lt.liked_username = :userId) " +
                    "SELECT t.track_id track_id " +
                    "FROM Tracks t, liked_avg " +
                    "WHERE ABS(t.danceability - liked_avg.avgDanceability) <= 0.5 " +
                    "AND ABS(t.energy - liked_avg.avgEnergy) <= 0.5 " +
                    "AND ABS(t.tempo - liked_avg.avgTempo) <= 5;", nativeQuery = true)
    List<String> getRecommendations(String userId);
}
