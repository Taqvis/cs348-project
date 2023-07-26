package com.example.cs348project.repository;

import com.example.cs348project.entity.Playlist;
import com.example.cs348project.entity.PlaylistID;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, PlaylistID> {

    @Query(value = "SELECT * FROM Playlists WHERE username = :ownerName AND playlist_name LIKE CONCAT('%',:playlistName,'%')", nativeQuery = true)
    List<Playlist> findPlaylistByOwnerAndPlaylistName(@Param("ownerName") final String ownerName, @Param("playlistName") final String playlistName);

    @Query(value = "SELECT * FROM Playlists WHERE playlist_name LIKE CONCAT('%',:playlistName,'%')", nativeQuery = true)
    List<Playlist> findPlaylistByPlaylistName(@Param("playlistName") String playlistName);

    @Query(value = "SELECT * FROM Playlists WHERE username = :username", nativeQuery = true)
    List<Playlist> findPlaylistByOwner(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Playlists SET playlist_name = :newName WHERE username = :username AND playlist_name = :oldName", nativeQuery = true)
    void renamePlaylist(String username, String oldName, String newName);

}
