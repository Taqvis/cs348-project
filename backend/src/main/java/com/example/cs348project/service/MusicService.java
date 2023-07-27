package com.example.cs348project.service;

import com.example.cs348project.entity.*;
import com.example.cs348project.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MusicService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackArtistRepository trackArtistRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private PlaylistLikeRepository playlistLikeRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;
    
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public List<Track> findTrackByName(final String name) {
        List<Track> tracks = trackRepository.findByName(name);
        List<TrackArtist> artists = findArtistsByName(name);
        artists.forEach(artist -> tracks.add(artist.track));

        return tracks;
    }

    public List<TrackArtist> findArtistsByName(final String name) {
        return trackArtistRepository.findByName(name);
    }

    public List<TrackArtist> getAllArtists() {
        return trackArtistRepository.findAll();
    }

    public List<String> getAlbumsByArtist(final String name) {
        return trackArtistRepository.getAlbumsByArtist(name);
    }

    public Float getAveragePopularityByArtist(final String name) {
        return trackArtistRepository.getAveragePopularityByArtist(name);
    }

    public List<String> getMostPopularGenreByArtist(final String name) {
        return trackArtistRepository.getMostPopGenres(name);
    }

    public Integer getTotalLikesByArtist(final String name) {
        return trackArtistRepository.getTotalLikesByArtist(name);
    }

    public List<Playlist> findPlaylistByOwnerAndPlaylistName(String username, String playlistName) {
        return playlistRepository.findPlaylistByOwnerAndPlaylistName(username, playlistName);
    }
    public List<Playlist> findPlaylistByPlaylistName(String playlistName) {
        return playlistRepository.findPlaylistByPlaylistName(playlistName);
    }

    public List<Playlist> getAllUserPlaylists(String username) {
        return playlistRepository.findPlaylistByOwner(username);
    }

    public Playlist createPlaylist(String username, String playlistName) {
        Playlist playlist = new Playlist(username, playlistName);
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(String username, String playlistName) {
        playlistRepository.deleteById(new PlaylistID(username, playlistName));
    }

    public void renamePlaylist(String username, String oldName, String newName) {
        Optional<Playlist> playlist = playlistRepository.findById(new PlaylistID(username, oldName));
        if (playlist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found");
        }

        Optional<Playlist> existingPlaylist = playlistRepository.findById(new PlaylistID(username, newName));
        if (existingPlaylist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Playlist with new name already exists");
        }

        playlistRepository.renamePlaylist(username, oldName, newName);
    }

    public PlaylistTrack addTrackToPlaylist(String username, String playlistName, String trackId) {
        return playlistTrackRepository.save(new PlaylistTrack(username, playlistName, trackId));
    }

    public void removeTrackFromPlaylist(String username, String playlistName, String trackId) {
        playlistTrackRepository.deleteById(new PlaylistTrackID(username, playlistName, trackId));
    }

    public PlaylistLike likePlaylist(String ownerName, String playlistName, String username) {
        return playlistLikeRepository.save(new PlaylistLike(ownerName, playlistName, username));
    }

    public void removePlaylistLike(String ownerName, String playlistName, String username) {
        playlistLikeRepository.deleteById(new PlaylistLikeID(ownerName, playlistName, username));
    }

    public List<Track> getDanceabilityRecommendations(String userId) {
        List<String> recommendedTrackIDs = trackRepository.getDanceabilityRecommendations(userId);
        return trackRepository.findAllById(recommendedTrackIDs);
    }

    public List<Track> getEnergyRecommendations(String userId) {
        List<String> recommendedTrackIDs = trackRepository.getEnergyRecommendations(userId);
        return trackRepository.findAllById(recommendedTrackIDs);
    }

    public List<Track> getTempoRecommendations(String userId) {
        List<String> recommendedTrackIDs = trackRepository.getTempoRecommendations(userId);
        return trackRepository.findAllById(recommendedTrackIDs);
    }

    public List<LeaderboardSpot> getLeaderboard() {
        return leaderboardRepository.findAll();
    }

}
