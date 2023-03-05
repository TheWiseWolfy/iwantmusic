package com.unwise.content_service;

import com.unwise.content_service.repositories.ArtistRepository;
import com.unwise.content_service.repositories.ContentRepository;
import com.unwise.content_service.repositories.CreditsRepository;
import com.unwise.content_service.repositories.TagTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

   // @Bean
    CommandLineRunner initDatabase(ContentRepository contentRepository,
                                   ArtistRepository artistRepository,
                                   CreditsRepository creditsRepository,
                                    TagTypesRepository tagTypesRepository) {

       return args -> {
       //     log.info("Preloading " + tagTypesRepository.save(new TagType("Rock")  ));
       };
    }
}