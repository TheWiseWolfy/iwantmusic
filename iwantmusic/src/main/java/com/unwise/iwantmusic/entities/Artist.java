package com.unwise.iwantmusic.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "artists")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Artist {
    private @Id String artist_identifier;
    private String artist_name;
    private Boolean is_active;

    public Artist(String artist_identifier, String artist_name, Boolean is_active) {
        this.artist_identifier = artist_identifier;
        this.artist_name = artist_name;
        this.is_active = is_active;
    }
}
