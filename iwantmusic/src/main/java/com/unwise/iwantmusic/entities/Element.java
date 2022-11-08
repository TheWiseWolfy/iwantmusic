package com.unwise.iwantmusic.entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Element {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private LocalDate publish_date;

    public Element(String name, LocalDate publish_date) {

        this.name = name;
        this.publish_date = publish_date;
    }
}
