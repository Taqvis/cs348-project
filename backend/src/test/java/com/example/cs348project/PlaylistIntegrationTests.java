package com.example.cs348project;

import com.example.cs348project.entity.Track;
import com.example.cs348project.entity.User;
import com.example.cs348project.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlaylistIntegrationTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private String auth;
    private final String username = "TEMPUSER";
    private final String password = "password";


    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername(username);
        user.setDisplayName(username);
        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        String plainCreds =  username + ":" + password;
        auth = "Basic " + new String(Base64.getEncoder().encode(plainCreds.getBytes()));
    }

    @AfterEach
    public void cleanup() {
        userRepository.delete(user);
    }

    @Test
    void getAllPlaylistsTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks", HttpMethod.GET, request, Track[].class);
        assertTrue(response.getBody().length > 10);
    }

    // test to search for tracks
    @Test
    void getSearchTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks/Nevada", HttpMethod.GET, request, Track[].class);
        log.info("{}", response.getBody().length);
        assertTrue(response.getBody().length == 1);
        assertTrue(response.getBody()[0].getTrackId().equals("02shCNmb6IvgB5jLqKjtkK"));
    }

    // test to search for track (when no such track name or artist name)
    @Test
    void getNoSearchTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks/Temporary Artist", HttpMethod.GET, request, Track[].class);
        assertTrue(response.getBody().length == 0);
    }

    // test to search for artist popularity when artist exists
    @Test
    void getArtistPopularityTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Float> response = restTemplate.exchange( "http://localhost:8080/artist/Linkin Park/popularity", HttpMethod.GET, request, Float.class);
        assertTrue(response.getBody() == 41.3333f);
    }

        // test to search for artist popularity when artist doesn't exist
    @Test
    void getNoArtistPopularityTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Float> response = restTemplate.exchange( "http://localhost:8080/artist/Temporary Artist/popularity", HttpMethod.GET, request, Float.class);
        assertTrue(response.getBody() == null);
    }

    // test to search for artist albums when artist exists
    @Test
    void getArtistAlbumsTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);
        ResponseEntity<String[]> response = restTemplate.exchange( "http://localhost:8080/artist/Linkin Park/albums", HttpMethod.GET, request, String[].class);
        assertTrue(response.getBody().length == 3);
        assertTrue(response.getBody()[0].equals("Hybrid Theory"));
        assertTrue(response.getBody()[1].equals("Waiting for the End"));
        assertTrue(response.getBody()[2].equals("Minutes to Midnight"));
    }

    // test to search for artist albums when artist doesn't exist
    @Test
    void getNoArtistAlbumsTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);
        ResponseEntity<String[]> response = restTemplate.exchange( "http://localhost:8080/artist/Temporary Artist/albums", HttpMethod.GET, request, String[].class);
        assertTrue(response.getBody().length == 0);
    }

    // test to search for artist most pop genres when artist exist
    @Test
    void getArtistGenresTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);
        ResponseEntity<String[]> response = restTemplate.exchange( "http://localhost:8080/artist/Linkin Park/genres", HttpMethod.GET, request, String[].class);
        assertTrue(response.getBody()[0].equals("grunge"));
        assertTrue(response.getBody()[1].equals("metal"));
    }

    //  test to search for artist most pop genres when artist doesn't exist
    @Test
    void getNoArtistGenresTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);
        ResponseEntity<String[]> response = restTemplate.exchange( "http://localhost:8080/artist/Temporary Artist/genres", HttpMethod.GET, request, String[].class);
        assertTrue(response.getBody().length == 0);
    }

    @Test
    void registerTest() {
        HttpHeaders headers = new HttpHeaders();

        User newUser = new User();
        user.setUsername("testUser");
        user.setDisplayName("Test User");
        user.setPassword(passwordEncoder.encode("testpassword"));
    }
}

