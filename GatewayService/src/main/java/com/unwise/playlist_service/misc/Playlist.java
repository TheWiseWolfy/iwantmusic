package com.unwise.playlist_service.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {
    @JsonProperty("id")

    private String id;
    @JsonProperty("playlist_name")
    private String playlist_name;

    @JsonProperty("owner_id")
    private String owner_id;

    @JsonProperty("creationDate")
    private LocalDate creationDate;

    @JsonProperty("songs")
    private List<PlaylistSong> songs;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistSong {
        @JsonProperty(value = "songId", required = true)
        private Long songId;

        @JsonProperty(value = "songName", required = false)
        private String songName = "";

        @JsonProperty(value = "songLink", required = false)
        private String songLink = "";

    }
}
