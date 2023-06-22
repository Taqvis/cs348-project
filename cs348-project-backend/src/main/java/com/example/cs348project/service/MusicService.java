package com.example.cs348project.service;

import com.example.cs348project.entity.ExampleEntity;
import com.example.cs348project.entity.TrackArtistEntity;
import com.example.cs348project.entity.TrackEntity;
import com.example.cs348project.repository.ExampleRepository;
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
    private ExampleRepository exampleRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackArtistRepository trackArtistRepository;

    public String helloWorld() {
        return "Hello World!";
    }

    public String createExampleEntity() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setName("Example name");
        exampleRepository.save(exampleEntity);
        return "Saved";
    }

    public String createTrackEntity() {
        TrackEntity trackEntity = new TrackEntity();
        trackEntity.setTrack_name("Example name");
        trackRepository.save(trackEntity);
        return "Saved";
    }
    
    public Iterable<TrackEntity> getAllTracks() {
        Iterable<TrackEntity> tracks = trackRepository.findAll();
        Iterable<TrackArtistEntity> artists = trackArtistRepository.findAll();

        // This filters the artists list for identical track_id, then assigns it to the TrackEntity
        for (TrackEntity track : tracks) {
            List<TrackArtistEntity> artistsList = ((Collection<TrackArtistEntity>) artists).stream().filter(artist -> artist.getTrack_id().equals(track.getTrack_id())).collect(Collectors.toList());
            track.setArtists(artistsList.stream().map(TrackArtistEntity::getArtist).collect(Collectors.toList()));
        }

        return tracks;
    }

    public Iterable<TrackEntity> findTrackByName(final String name) {
        Iterable<TrackEntity> tracks = trackRepository.findByName(name);
        List<TrackArtistEntity> artists = trackArtistRepository.findByName(name);

        // This filters the artists list for identical track_id, then assigns it to the TrackEntity
        for (TrackEntity track : tracks) {
            List<TrackArtistEntity> artistsList = artists.stream().filter(artist -> artist.getTrack_id().equals(track.getTrack_id())).collect(Collectors.toList());
            track.setArtists(artistsList.stream().map(TrackArtistEntity::getArtist).collect(Collectors.toList()));
        }

        return tracks;
    }

    public Iterable<TrackArtistEntity> findArtistsByName(final String name) {
        return trackArtistRepository.findByName(name);
    }

    public Iterable<TrackArtistEntity> findArtistsById(final String id) {
        return trackArtistRepository.findByTrackId(id);
    }

    public Iterable<TrackArtistEntity> getAllArtists() {
        return trackArtistRepository.findAll();
    }
}
