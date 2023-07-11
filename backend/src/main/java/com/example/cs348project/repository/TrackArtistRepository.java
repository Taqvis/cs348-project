package com.example.cs348project.repository;

import com.example.cs348project.entity.TrackArtist;
import com.example.cs348project.entity.TrackArtistID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackArtistRepository extends JpaRepository<TrackArtist, TrackArtistID> {

    @Query(value = "SELECT * FROM Track_Artists WHERE artist LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<TrackArtist> findByName(@Param("name") final String name);

	@Query(value = "SELECT * FROM Track_Artists WHERE track_id = :id", nativeQuery = true)
    List<TrackArtist> findByTrackId(@Param("id") final String id);
}
