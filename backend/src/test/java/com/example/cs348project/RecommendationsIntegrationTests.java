package com.example.cs348project;

import com.example.cs348project.entity.*;
import com.example.cs348project.repository.PlaylistLikeRepository;
import com.example.cs348project.repository.PlaylistRepository;
import com.example.cs348project.repository.PlaylistTrackRepository;
import com.example.cs348project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RecommendationsIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    private PlaylistLikeRepository playlistLikeRepository;

    private final String rootURL = "http://localhost:8080/";

    private User user;

    private String auth;

    private final String PLAYLIST_NAME = "TEMP_PLAYLIST";

    private final String TRACK_ID = "02shCNmb6IvgB5jLqKjtkK";

    @BeforeEach
    public void setup() {
        String username = "TEMP_USER";
        String password = "password";

        user = new User();
        user.setUsername(username);
        user.setDisplayName(username);
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        String plainCreds =  username + ":" + password;
        auth = "Basic " + new String(Base64.getEncoder().encode(plainCreds.getBytes()));

        Playlist playlist = new Playlist(username, PLAYLIST_NAME);
        playlistRepository.save(playlist);

        PlaylistTrack playlistTrack = new PlaylistTrack(username, PLAYLIST_NAME, TRACK_ID);
        playlistTrackRepository.save(playlistTrack);

        PlaylistLike playlistLike = new PlaylistLike(username, PLAYLIST_NAME, username);
        playlistLikeRepository.save(playlistLike);
    }

    @AfterEach
    public void cleanup() {
        userRepository.delete(user);
    }

    @Test
    public void getEnergyRecommendationsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( rootURL + "recommend/energy/" + user.getUsername(), HttpMethod.GET, request, Track[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        Track[] tracks = response.getBody();
        assertNotNull(tracks);
        assertTrue(tracks.length > 0);
        assertTrue(Arrays.stream(tracks).anyMatch(track -> track.getTrackId().equals(TRACK_ID)));
    }

    @Test
    public void getDanceabilityRecommendationsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( rootURL + "recommend/danceability/" + user.getUsername(), HttpMethod.GET, request, Track[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        Track[] tracks = response.getBody();
        assertNotNull(tracks);
        assertTrue(tracks.length > 0);
        assertTrue(Arrays.stream(tracks).anyMatch(track -> track.getTrackId().equals(TRACK_ID)));
    }

    @Test
    public void getTempoRecommendationsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( rootURL + "recommend/tempo/" + user.getUsername(), HttpMethod.GET, request, Track[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        Track[] tracks = response.getBody();
        assertNotNull(tracks);
        assertTrue(tracks.length > 0);
        assertTrue(Arrays.stream(tracks).anyMatch(track -> track.getTrackId().equals(TRACK_ID)));
    }

}
