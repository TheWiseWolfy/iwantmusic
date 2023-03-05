package com.unwise.content_service.controllers;

import com.unwise.content_service.entities.Artist;
import com.unwise.content_service.repositories.ArtistRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {

    private final ArtistRepository repository;

    ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/artists")
    List<Artist> all() {
        return repository.findAll();
    }

}
