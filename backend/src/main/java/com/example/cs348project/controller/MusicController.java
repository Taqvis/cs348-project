package com.example.cs348project.controller;

import com.example.cs348project.entity.*;
import com.example.cs348project.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping(path = "/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Track> getAllTracks() {
        // This returns a JSON or XML with the users
        return musicService.getAllTracks();
    }

    @GetMapping(path = "/tracks/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Track> findTrackByName(@PathVariable final String name){
        return musicService.findTrackByName(name);
    }

    @GetMapping(path = "/artists", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrackArtist> getAllArtists(){
        return musicService.getAllArtists();
    }

    @GetMapping(path = "/artists/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrackArtist> findArtistsByName(@PathVariable final String name){
        return musicService.findArtistsByName(name);
    }

    @GetMapping(path = "/artist/{name}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<String> getAlbumByName(@PathVariable final String name) {
        return musicService.getAlbumsByArtist(name);
    }

    @GetMapping(path = "/artist/{name}/popularity", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Float getAveragePopularityByArtist(@PathVariable final String name) {
        return musicService.getAveragePopularityByArtist(name);
    }

    @GetMapping(path = "/artist/{name}/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<String> getMostPopularGenreByArtist(@PathVariable final String name) {
        return musicService.getMostPopularGenreByArtist(name);
    }

    @GetMapping(path = "/artist/{name}/totalLikes", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer getTotalLikesByArtist(@PathVariable final String name) {
        return musicService.getTotalLikesByArtist(name) == null ? 0 : musicService.getTotalLikesByArtist(name);
    }

    @GetMapping(path = "/playlist/{username}/{playlistName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Playlist> findPlaylistByOwnerAndPlaylistName(@PathVariable String username, @PathVariable String playlistName) {
        return musicService.findPlaylistByOwnerAndPlaylistName(username, playlistName);
    }

    @GetMapping(path = "/playlist/{playlistName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Playlist> findPlaylistByPlaylistName(@PathVariable String playlistName) {
        return musicService.findPlaylistByPlaylistName(playlistName);
    }

    @GetMapping(path = "/user/playlist/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Playlist> getAllUserPlaylists(@PathVariable String username) {
        return musicService.getAllUserPlaylists(username);
    }

    @PostMapping(path = "/playlist/{username}/{playlistName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Playlist createPlaylist(@PathVariable String username, @PathVariable String playlistName) {
        return musicService.createPlaylist(username, playlistName);
    }

    @DeleteMapping(path = "/playlist/{username}/{playlistName}")
    public void deletePlaylist(@PathVariable String username, @PathVariable String playlistName) {
        musicService.deletePlaylist(username, playlistName);
    }


    @PatchMapping(path = "/playlist/{username}/{oldName}/{newName}")
    public void renamePlaylist(@PathVariable String username, @PathVariable String oldName, @PathVariable String newName) {
        musicService.renamePlaylist(username, oldName, newName);
    }

    @PostMapping(path = "/playlist/{username}/{playlistName}/{trackId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlaylistTrack addTrackToPlaylist(@PathVariable String username, @PathVariable String playlistName, @PathVariable String trackId) {
        return musicService.addTrackToPlaylist(username, playlistName, trackId);
    }

    @DeleteMapping(path = "/playlist/{username}/{playlistName}/{trackId}")
    public void removeTrackToPlaylist(@PathVariable String username, @PathVariable String playlistName, @PathVariable String trackId) {
        musicService.removeTrackFromPlaylist(username, playlistName, trackId);
    }

    @PostMapping(path = "/like/{ownerName}/{playlistName}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlaylistLike likePlaylist(@PathVariable String ownerName, @PathVariable String playlistName, @PathVariable String username) {
        return musicService.likePlaylist(ownerName, playlistName, username);
    }

    @DeleteMapping(path = "/like/{ownerName}/{playlistName}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removePlaylistLike(@PathVariable String ownerName, @PathVariable String playlistName, @PathVariable String username) {
        musicService.removePlaylistLike(ownerName, playlistName, username);
    }

    @GetMapping(path = "/recommend/danceability/{userId}")
    public List<Track> getDanceabilityRecommendations(@PathVariable(name = "userId") String userId) {
        return musicService.getDanceabilityRecommendations(userId);
    }

    @GetMapping(path = "/recommend/energy/{userId}")
    public List<Track> getEnergyRecommendations(@PathVariable(name = "userId") String userId) {
        return musicService.getEnergyRecommendations(userId);
    }

    @GetMapping(path = "/recommend/tempo/{userId}")
    public List<Track> getTempoRecommendations(@PathVariable(name = "userId") String userId) {
        return musicService.getTempoRecommendations(userId);
    }

    @GetMapping(path = "/leaderboard")
    public List<LeaderboardSpot> getLeaderboard() {
        return musicService.getLeaderboard();
    }

}
