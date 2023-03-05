package com.unwise.content_service.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tag_types")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class TagType {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long tag_type_id;
    private String tag_type_string;

    public TagType(String tag_type_string) {
        this.tag_type_string = tag_type_string;
    }
}
