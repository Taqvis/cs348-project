package com.example.cs348project.controller;

import com.example.cs348project.entity.TrackArtist;
import com.example.cs348project.entity.Track;
import com.example.cs348project.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping(path = "/test/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Track> getAllTracks() {
        // This returns a JSON or XML with the users
        return musicService.getAllTracks();
    }

    @GetMapping(path = "/test/tracks/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Track> findTrackByName(@PathVariable final String name){
        return musicService.findTrackByName(name);
    }

    @GetMapping(path = "/test/artists", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrackArtist> getAllArtists(){
        return musicService.getAllArtists();
    }

    @GetMapping(path = "/test/artists/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<TrackArtist> findArtistsByName(@PathVariable final String name){
        return musicService.findArtistsByName(name);
    }
}
