package com.unwise.playlist_service.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unwise.playlist_service.misc.Content;
import com.unwise.playlist_service.misc.Playlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class PlaylistService {
    Logger logger = LoggerFactory.getLogger(ContentService.class);


    public Optional<Playlist> getPlaylistById(String playlist_id){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( "http://localhost:8090/playlists/" + playlist_id))
                    .GET()
                    .build();

            var response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Playlist playlist = mapper.readValue(response.body(), Playlist.class);
            return Optional.of(playlist);
        }
        catch (Exception e){
            logger.error(e.toString());
        }
        return Optional.empty();
    }

    public List<Playlist> getAllPlaylists() {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create( "http://localhost:8090/playlists/"))
                    .GET()
                    .build();

            var response =  client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Playlist> playlists = mapper.readValue(response.body(), new TypeReference<List<Playlist>>() {});
            return playlists;
        }
        catch (Exception e){
            logger.error(e.toString());
        }
        return Collections.emptyList();
    }

    public HttpResponse<String> putPlaylist(Playlist playlist, String playlist_id){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String playlist_body = mapper.writeValueAsString(playlist);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8090/playlists/" + playlist_id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(playlist_body))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e){
            logger.error(e.toString());
        }
        return null;
    }


}
