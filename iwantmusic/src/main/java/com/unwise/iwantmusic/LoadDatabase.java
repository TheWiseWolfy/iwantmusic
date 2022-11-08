package com.unwise.iwantmusic;

import com.unwise.iwantmusic.entities.Element;
import com.unwise.iwantmusic.repositories.ElementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Date;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ElementRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Element("Cruciada Rock", LocalDate.of(2015, 3, 2) )));
            log.info("Preloading " + repository.save(new Element("Cruciada Metal", LocalDate.of(2017, 4, 7))));
        };
    }
}