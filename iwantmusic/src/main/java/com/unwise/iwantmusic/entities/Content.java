package com.unwise.iwantmusic.entities;

import com.unwise.iwantmusic.misc.ContentType;
import lombok.*;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Content {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long content_id;
    private String content_name;
    private LocalDate content_date;
    private ContentType content_type;
    private Long content_parent;

    public Content(String content_name, LocalDate content_date,  ContentType content_type, Long content_parent) {
        this.content_name = content_name;
        this.content_date = content_date;
        this.content_type = content_type;
        this.content_parent = content_parent;
    }
}
