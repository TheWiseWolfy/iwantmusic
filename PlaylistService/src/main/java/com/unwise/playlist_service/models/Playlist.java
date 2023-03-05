package com.unwise.playlist_service.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "iwantmusic_playlists")
public class Playlist {

    @Id
    private String id;

    private String playlist_name;
    private String owner_id;
    private LocalDate creationDate;
    private List<PlaylistSong> songs;
    public Playlist( String playlist_name, LocalDate creationDate, List<PlaylistSong> songs) {
        super();
        this.id = null;
        this.playlist_name = playlist_name;
        this.creationDate = creationDate;
        this.songs = songs;
    }
}
