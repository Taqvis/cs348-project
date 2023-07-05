package com.example.cs348project.service;

import com.example.cs348project.entity.TrackArtist;
import com.example.cs348project.entity.Track;
import com.example.cs348project.repository.TrackArtistRepository;
import com.example.cs348project.repository.TrackRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackArtistRepository trackArtistRepository;

    public String createTrackEntity() {
        Track trackEntity = new Track();
        trackEntity.setTrackName("Example name");
        trackRepository.save(trackEntity);
        return "Saved";
    }
    
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public List<Track> findTrackByName(final String name) {
        return trackRepository.findByName(name);
    }

    public List<TrackArtist> findArtistsByName(final String name) {
        return trackArtistRepository.findByName(name);
    }

    public List<TrackArtist> findArtistsById(final String id) {
        return trackArtistRepository.findByTrackId(id);
    }

    public List<TrackArtist> getAllArtists() {
        return trackArtistRepository.findAll();
    }
}
