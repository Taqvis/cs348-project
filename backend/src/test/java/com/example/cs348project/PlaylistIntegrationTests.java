package com.example.cs348project;

import com.example.cs348project.entity.Track;
import com.example.cs348project.entity.User;
import com.example.cs348project.repository.UserRepository;
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

//        // for json body
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks", HttpMethod.GET, request, Track[].class);
        assertTrue(response.getBody().length > 10);
    }


}
