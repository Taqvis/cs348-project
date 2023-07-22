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

    @Query(value = "SELECT DISTINCT album_name FROM Tracks INNER JOIN Track_Artists as Artists ON Tracks.track_id = Artists.track_id WHERE Artists.artist = :name", nativeQuery = true)
    List<String> getAlbumsByArtist(@Param("name") final String name); 

    @Query(value ="SELECT AVG(popularity) FROM Tracks INNER JOIN Track_Artists as Artists ON Tracks.track_id = Artists.track_id GROUP BY Artists.artist HAVING Artists.artist = :name", nativeQuery = true)
    Float getAveragePopularityByArtist(@Param("name") final String name); 

    @Query(value = "SELECT track_genre FROM Tracks INNER JOIN Track_Artists as Artists ON Tracks.track_id = Artists.track_id GROUP BY Artists.artist, Tracks.track_genre HAVING Artists.artist = :name ORDER BY COUNT(*) DESC, Tracks.track_genre ASC LIMIT 2", nativeQuery = true)
    List<String> getMostPopGenres(@Param("name") final String name);

    @Query(value = "SELECT COUNT(*) FROM Playlist_Tracks AS pt INNER JOIN Playlist_Likes AS pl ON pt.playlist_name = pl.playlist_name AND pt.username = pl.owner_username INNER JOIN Track_Artists ta ON ta.track_id = pt.track_id GROUP BY ta.artist HAVING ta.artist = :name", nativeQuery = true)
    Integer getTotalLikesByArtist(@Param("name") final String name);


}
