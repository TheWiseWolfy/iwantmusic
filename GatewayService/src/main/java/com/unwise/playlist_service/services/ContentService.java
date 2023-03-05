package com.unwise.playlist_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unwise.playlist_service.misc.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service

public class ContentService {

    Logger logger = LoggerFactory.getLogger(ContentService.class);

    public Optional<Content> getContentByID(Long i)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/contents/" + i))
                    .GET()
                    .build();

            //Here we read the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Recuperam ce content avem nevoie
            ObjectMapper mapper = new ObjectMapper();
            Content content = mapper.readValue(response.body(), Content.class);
            return Optional.of(content);
        }
        catch (Exception e){
            logger.error( e.toString() );
        }
        return Optional.empty();
    }
}
