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
public class LeaderboardIntegrationTests {

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

    private User user1;

    private User user2;

    private String auth1;

    private String auth2;

    private final String PLAYLIST_NAME = "TEMP_PLAYLIST";

    private final String TRACK_ID = "02shCNmb6IvgB5jLqKjtkK";

    @BeforeEach
    public void setup() {
        String username1 = "TEMP_USER1";
        String password = "password";

        user1 = new User();
        user1.setUsername(username1);
        user1.setDisplayName(username1);
        user1.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user1);
        assertNotNull(savedUser);
        String plainCreds =  username1 + ":" + password;
        auth1 = "Basic " + new String(Base64.getEncoder().encode(plainCreds.getBytes()));

        Playlist playlist = new Playlist(username1, PLAYLIST_NAME);
        playlistRepository.save(playlist);

        PlaylistLike playlistLike = new PlaylistLike(username1, PLAYLIST_NAME, username1);
        playlistLikeRepository.save(playlistLike);

        String username2 = "TEMP_USER2";

        user2 = new User();
        user2.setUsername(username2);
        user2.setDisplayName(username2);
        user2.setPassword(passwordEncoder.encode(password));
        savedUser = userRepository.save(user2);
        assertNotNull(savedUser);
        plainCreds =  username2 + ":" + password;
        auth2 = "Basic " + new String(Base64.getEncoder().encode(plainCreds.getBytes()));

        playlist = new Playlist(username2, PLAYLIST_NAME);
        playlistRepository.save(playlist);

        playlistLike = new PlaylistLike(username2, PLAYLIST_NAME, username2);
        playlistLikeRepository.save(playlistLike);

        playlistLike = new PlaylistLike(username1, PLAYLIST_NAME, username2);
        playlistLikeRepository.save(playlistLike);
    }

    @AfterEach
    public void cleanup() {
        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @Test
    public void getLeaderboardTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth1);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<LeaderboardSpot[]> response = restTemplate.exchange( rootURL + "leaderboard/", HttpMethod.GET, request, LeaderboardSpot[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        LeaderboardSpot[] leaderboard = response.getBody();
        assertNotNull(leaderboard);
        assertTrue(leaderboard.length == 2);
    }

}
