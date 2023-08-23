package com.conrua.playspotifymusic.services;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.conrua.playspotifymusic.spotify.SpotifyTokenResponse;
import com.conrua.playspotifymusic.spotify.SpotifyTrackResponse;


@Service
public class SpotifyApiService {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1";

    @Autowired
    private final RestTemplate restTemplate;

    public SpotifyApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSongUri(String songId) {
        String apiUrl = SPOTIFY_API_BASE_URL + "/tracks/" + songId;
        // Setup headers with authorization token
        String authorizationHeader = "Bearer " + getAccessToken();

        // Configure headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        // Create a request entity with headers 
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the API call and parse the response
        ResponseEntity<SpotifyTrackResponse> responseEntity = restTemplate.exchange(
            apiUrl, HttpMethod.GET, requestEntity, SpotifyTrackResponse.class);
        
        // Extract the URI of the song from the response
        String songUri = responseEntity.getBody().getUri();
        return songUri;
    }

    private String getAccessToken() {
        String url = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);    
        
        ResponseEntity<SpotifyTokenResponse> responseEntity = restTemplate.exchange(
            url, HttpMethod.POST, entity, SpotifyTokenResponse.class);
            
        String accessToken = responseEntity.getBody().getAccessToken();
        return accessToken;
    }
 
}
