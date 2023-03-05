package com.unwise.playlist_service;

import com.unwise.playlist_service.models.Playlist;
import com.unwise.playlist_service.models.PlaylistSong;
import com.unwise.playlist_service.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PlaylistService service) {

        List<PlaylistSong> songs = new ArrayList<>();

        songs.add( new PlaylistSong(37L, "WakaWaka", "www.google.com"));
        songs.add( new PlaylistSong(43L, "NUMANUMA", "www.google.com"));

        //Playlist myPlaylist = new Playlist("wakawak", "Ultra Party", LocalDate.now(), songs);

       // service.createAPlaylist(myPlaylist);
       return args -> {
           log.info("SUCCESS ");
       };
    }
}