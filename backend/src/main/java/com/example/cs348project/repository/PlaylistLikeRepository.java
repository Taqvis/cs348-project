package com.example.cs348project.repository;

import com.example.cs348project.entity.PlaylistLike;
import com.example.cs348project.entity.PlaylistLikeID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, PlaylistLikeID> {

}
