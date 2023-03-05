package com.unwise.content_service.entities;

import com.unwise.content_service.misc.ContentType;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Content {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long content_id;
    private String content_name;
    private LocalDate content_date;
    @Enumerated(EnumType.ORDINAL)
    private ContentType content_type;

    @Nullable
    private Long content_parent;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)  //Tags will stay even if we delete all songs
    @JoinTable(
            name = "song_tags",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_type_id")
    )
    Set<TagType> associatedTags;

    @ToString.Exclude
    @OneToMany(mappedBy = "content", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Credits> credits;

    public Content(String content_name, LocalDate content_date, ContentType content_type, Long content_parent) {
        this.content_name = content_name;
        this.content_date = content_date;
        this.content_type = content_type;
        this.content_parent = content_parent;
    }

    public Content(String content_name, LocalDate content_date, ContentType content_type, @Nullable Long content_parent, Set<TagType> asociatedTags, Set<Credits> credits) {
        this.content_name = content_name;
        this.content_date = content_date;
        this.content_type = content_type;
        this.content_parent = content_parent;
        this.associatedTags = asociatedTags;
        this.credits = credits;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += content_id.hashCode();
        hash += content_name.hashCode();
        hash += content_date.hashCode();
        hash += content_type.hashCode();
        if(content_parent != null)  hash += content_parent.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Content other = (Content) obj;

        return Objects.equals(this.content_id, other.content_id);
    }

}
