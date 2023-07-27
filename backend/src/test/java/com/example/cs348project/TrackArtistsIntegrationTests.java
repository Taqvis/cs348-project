package com.example.cs348project;

import com.example.cs348project.entity.Track;
import com.example.cs348project.entity.User;
import com.example.cs348project.repository.UserRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TrackArtistsIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private String auth;


    @BeforeEach
    public void setup() {
        final String username = "TEMP_USER";
        final String password = "password";

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
        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks/Nevada", HttpMethod.GET, request, Track[].class);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("02shCNmb6IvgB5jLqKjtkK", response.getBody()[0].getTrackId());
    }

    // test to search for track (when no such track name or artist name)
    @Test
    void getNoSearchTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);
        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks/Temporary Artist", HttpMethod.GET, request, Track[].class);
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    // test to search for artist popularity when artist exists
    @Test
    void getArtistPopularityTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);
        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(headers);

        ResponseEntity<Float> response = restTemplate.exchange( "http://localhost:8080/artist/Linkin Park/popularity", HttpMethod.GET, request, Float.class);
        assertEquals(41.3333f, response.getBody());
    }

        // test to search for artist popularity when artist doesn't exist
    @Test
    void getNoArtistPopularityTest() {
        HttpHeaders headers = new HttpHeaders();
        System.err.println(auth);
        headers.add("Authorization", auth);
        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(headers);

        ResponseEntity<Float> response = restTemplate.exchange( "http://localhost:8080/artist/Temporary Artist/popularity", HttpMethod.GET, request, Float.class);
        assertNull(response.getBody());
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
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
        assertEquals("Hybrid Theory", response.getBody()[0]);
        assertEquals("Waiting for the End", response.getBody()[1]);
        assertEquals("Minutes to Midnight", response.getBody()[2]);
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
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
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
        assertNotNull(response.getBody());
        assertEquals("grunge", response.getBody()[0]);
        assertEquals("metal", response.getBody()[1]);
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
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
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

