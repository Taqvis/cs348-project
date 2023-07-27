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

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlaylistsIntegrationTests {

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

    private final String ROOT_URL = "http://localhost:8080/";

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
    }

    @AfterEach
    public void cleanup() {
        userRepository.delete(user);
    }

    @Test
    public void createPlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String newPlaylistName = "TEMP_PLAYLIST2";
        ResponseEntity<Playlist> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + newPlaylistName, HttpMethod.POST, request, Playlist.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        Playlist playlist = response.getBody();

        assertNotNull(playlist);
        assertEquals(playlist.getPlaylistName(), newPlaylistName);
        assertEquals(playlist.getUsername(), user.getUsername());

        playlistRepository.delete(playlist);
    }

    @Test
    public void findPlaylistByOwnerAndNameTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Playlist[]> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME, HttpMethod.GET, request, Playlist[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().length, 1);
        Playlist playlist = response.getBody()[0];

        assertNotNull(playlist);
        assertEquals(playlist.getPlaylistName(), PLAYLIST_NAME);
        assertEquals(playlist.getUsername(), user.getUsername());
    }

    @Test
    public void findPlaylistByNameTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Playlist[]> response = restTemplate.exchange( ROOT_URL + "playlist/" + PLAYLIST_NAME, HttpMethod.GET, request, Playlist[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().length, 1);
        Playlist playlist = response.getBody()[0];

        assertNotNull(playlist);
        assertEquals(playlist.getPlaylistName(), PLAYLIST_NAME);
        assertEquals(playlist.getUsername(), user.getUsername());
    }

    @Test
    public void getAllUserPlaylistsTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Playlist[]> response = restTemplate.exchange( ROOT_URL + "user/playlist/" + user.getUsername(), HttpMethod.GET, request, Playlist[].class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        Playlist[] playlists = response.getBody();

        assertNotNull(playlists);
        assertEquals(playlists.length, 1);
        assertEquals(playlists[0].getPlaylistName(), PLAYLIST_NAME);
        assertEquals(playlists[0].getUsername(), user.getUsername());
    }

    @Test
    public void deletePlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        System.err.println(ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME);

        ResponseEntity<Void> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME, HttpMethod.DELETE, request, Void.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        Optional<Playlist> playlist = playlistRepository.findById(new PlaylistID(user.getUsername(), PLAYLIST_NAME));
        assertTrue(playlist.isEmpty());
    }

    @Test
    public void renamePlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String newName = "TEMP_PLAYLIST_2";
        ResponseEntity<Void> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME + "/" + newName, HttpMethod.PATCH, request, Void.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        Optional<Playlist> newPlaylist = playlistRepository.findById(new PlaylistID(user.getUsername(), newName));
        assertTrue(newPlaylist.isPresent());
        assertEquals(newPlaylist.get().getUsername(), user.getUsername());

        Optional<Playlist> oldPlaylist = playlistRepository.findById(new PlaylistID(user.getUsername(), PLAYLIST_NAME));
        assertTrue(oldPlaylist.isEmpty());

        playlistRepository.delete(newPlaylist.get());
    }

    @Test
    public void addTrackToPlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String newPlaylistTrackId = "07F3l9pvdeysyHwIjNHowQ";
        ResponseEntity<PlaylistTrack> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME + "/" + newPlaylistTrackId, HttpMethod.POST, request, PlaylistTrack.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        PlaylistTrack playlistTrack = response.getBody();
        assertNotNull(playlistTrack);
        assertEquals(playlistTrack.getTrackId(), newPlaylistTrackId);
        assertEquals(playlistTrack.getPlaylistName(), PLAYLIST_NAME);
        assertEquals(playlistTrack.getUsername(), user.getUsername());

        playlistTrackRepository.delete(playlistTrack);
    }

    @Test
    public void removeTrackFromPlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = restTemplate.exchange( ROOT_URL + "playlist/" + user.getUsername() + "/" + PLAYLIST_NAME + "/" + TRACK_ID, HttpMethod.DELETE, request, Void.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        Optional<PlaylistTrack> playlistTrack = playlistTrackRepository.findById(new PlaylistTrackID(user.getUsername(), PLAYLIST_NAME, TRACK_ID));
        assertTrue(playlistTrack.isEmpty());
    }

    public void likePlaylistTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<PlaylistLike> response = restTemplate.exchange( ROOT_URL + "like/" + user.getUsername() + "/" + PLAYLIST_NAME + "/" + user.getUsername(), HttpMethod.POST, request, PlaylistLike.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        PlaylistLike playlistLike = response.getBody();
        assertNotNull(playlistLike);
        assertEquals(playlistLike.getPlaylistName(), PLAYLIST_NAME);
        assertEquals(playlistLike.getOwnerUsername(), user.getUsername());
        assertEquals(playlistLike.getLikedUsername(), user.getUsername());
    }

    public void removePlaylistLikeTest() {
        PlaylistLike playlistLike = new PlaylistLike(user.getUsername(), PLAYLIST_NAME, user.getUsername());
        playlistLikeRepository.save(playlistLike);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> response = restTemplate.exchange( ROOT_URL + "like/" + user.getUsername() + "/" + PLAYLIST_NAME + "/" + user.getUsername(), HttpMethod.DELETE, request, Void.class);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());

        Optional<PlaylistLike> oldPlaylistLike = playlistLikeRepository.findById(new PlaylistLikeID(user.getUsername(), PLAYLIST_NAME, user.getUsername()));
        assertTrue(oldPlaylistLike.isEmpty());
    }


}
