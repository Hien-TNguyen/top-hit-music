package com.conrua.playspotifymusic.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyTrackResponse {
    private String uri;

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }
}
