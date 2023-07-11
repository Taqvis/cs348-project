package com.example.cs348project.service;

import com.example.cs348project.entity.*;
import com.example.cs348project.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserRepository userRepository;
    
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public List<Track> findTrackByName(final String name) {
        return trackRepository.findByName(name);
    }

    public List<TrackArtist> findArtistsByName(final String name) {
        return trackArtistRepository.findByName(name);
    }

    public List<TrackArtist> getAllArtists() {
        return trackArtistRepository.findAll();
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

}
