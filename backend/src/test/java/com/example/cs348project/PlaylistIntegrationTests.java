package com.example.cs348project;

import com.example.cs348project.entity.Track;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlaylistIntegrationTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void getAllPlaylistsTest() {
        HttpHeaders headers = new HttpHeaders();
        String plainCreds = "user6:password";
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode(plainCreds.getBytes())));

//        // for json body
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("id", "1");
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        HttpEntity<MultiValueMap<String, String>>  request = new HttpEntity<>(map, headers);

        ResponseEntity<Track[]> response = restTemplate.exchange( "http://localhost:8080/tracks", HttpMethod.GET, request, Track[].class);
        assertTrue(response.getBody().length > 100);
    }


}
