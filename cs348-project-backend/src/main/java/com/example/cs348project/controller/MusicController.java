package com.example.cs348project.controller;

import com.example.cs348project.entity.TrackArtistEntity;
import com.example.cs348project.entity.TrackEntity;
import com.example.cs348project.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping(path = "/test/hello")
    public String helloWorld() {
        return musicService.helloWorld();
    }

    @PostMapping(path = "/test/entity")
    public String createExampleEntity() {
        return musicService.createExampleEntity();
    }

    @GetMapping(path="/test/tracks")
    public @ResponseBody Iterable<TrackEntity> getAllTracks() {
        // This returns a JSON or XML with the users
        return musicService.getAllTracks();
    }

    @GetMapping("/test/tracks/{name}")
    public Iterable<TrackEntity> findByName(@PathVariable final String name){
        return musicService.findTrackByName(name);
    }

    @GetMapping("/test/tracks/artists")
    public Iterable<TrackArtistEntity> getAllArtistsByName(){
        return musicService.getAllArtists();
    }

    @GetMapping("/test/tracks/artists/{name}")
    public Iterable<TrackArtistEntity> findArtistsByName(@PathVariable final String name){
        return musicService.findArtistsByName(name);
    }
}
