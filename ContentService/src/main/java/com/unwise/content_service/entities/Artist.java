package com.unwise.content_service.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "artists")
@Getter @Setter @NoArgsConstructor @ToString
public class Artist {
    private @Id String artist_identifier;
    private String artist_name;
    private Boolean is_active;

    //@ToString.Exclude
    //@OneToMany(mappedBy = "artist",fetch=FetchType.LAZY)
    //Set<Credits> credits;

    public Artist(String artist_identifier, String artist_name, Boolean is_active) {
        this.artist_identifier = artist_identifier;
        this.artist_name = artist_name;
        this.is_active = is_active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += artist_identifier.hashCode();
        hash += artist_name.hashCode();
        hash += is_active.hashCode();

        return hash;
    }

}
