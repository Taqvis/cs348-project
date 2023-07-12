package com.example.cs348project.repository;

import com.example.cs348project.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, String> {

    @Query(value = "SELECT * FROM Tracks WHERE track_name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<Track> findByName(@Param("name") final String name);
}
