package com.example.cs348project.repository;

import com.example.cs348project.entity.PlaylistTrack;
import com.example.cs348project.entity.PlaylistTrackID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackID> {

}
