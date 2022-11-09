package com.unwise.iwantmusic;

import com.unwise.iwantmusic.entities.Artist;
import com.unwise.iwantmusic.entities.Content;
import com.unwise.iwantmusic.misc.ContentType;
import com.unwise.iwantmusic.repositories.ArtistRepository;
import com.unwise.iwantmusic.repositories.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //*
    @Bean
    CommandLineRunner initDatabase(ContentRepository contentRepository, ArtistRepository artistRepository) {

        return args -> {
            log.info("Preloading " + contentRepository.save(new Content("Cruciada Rock", LocalDate.of(2015, 3, 2) , ContentType.single, null)));
            log.info("Preloading " + artistRepository.save(new Artist("alexmetalistu655", "Alex Metalistu"  , Boolean.TRUE)));

        };
    }
}