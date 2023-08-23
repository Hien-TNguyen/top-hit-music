package com.conrua.playspotifymusic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.conrua.playspotifymusic.services.SpotifyApiService;

@RestController
public class MusicController {
    @Autowired
    private final SpotifyApiService spotifyApiService;

    public MusicController(SpotifyApiService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    @GetMapping("/play/{songId}") 
    public String playSong(@PathVariable String songId) {
        String songUri = spotifyApiService.getSongUri(songId);
        return "Playing song: " + songUri;
    }
}
