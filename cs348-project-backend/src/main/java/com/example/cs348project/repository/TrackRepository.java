package com.example.cs348project.repository;

import com.example.cs348project.entity.TrackEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends CrudRepository<TrackEntity, String> {

    @Query(value = "SELECT * FROM Tracks WHERE track_name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<TrackEntity> findByName(@Param("name") final String name);
}
