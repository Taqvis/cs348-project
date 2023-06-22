package com.example.cs348project.repository;

import com.example.cs348project.entity.TrackArtistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackArtistRepository extends CrudRepository<TrackArtistEntity, String> {

    @Query(value = "SELECT * FROM Track_Artists WHERE EXISTS (SELECT track_id FROM Tracks WHERE Tracks.track_id = Track_Artists.track_id AND Tracks.track_name LIKE CONCAT('%',:name,'%'))", nativeQuery = true)
    List<TrackArtistEntity> findByName(@Param("name") final String name);

	@Query(value = "SELECT * FROM Track_Artists WHERE track_id = :id", nativeQuery = true)
    List<TrackArtistEntity> findByTrackId(@Param("id") final String id);
}
