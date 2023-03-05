package com.unwise.content_service.entities;

import com.unwise.content_service.embeddable.CreditKey;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Credits {

    @EmbeddedId
    CreditKey id = new CreditKey();

    @ManyToOne(fetch=FetchType.EAGER)
    @MapsId("contentID")
    @JoinColumn(name = "content_id")
    Content content;

    @ManyToOne(fetch=FetchType.EAGER)
    @MapsId("artistID")
    @JoinColumn(name = "artist_identifier")
    Artist artist;

    private int artist_index;

    public Credits(Artist artist, Content content, int artist_index) {
        this.artist = artist;
        this.content = content;
        this.artist_index = artist_index;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += id.hashCode();
//     //   hash += content.hashCode();
//      //  hash += artist.hashCode();
//        hash += artist_index;
//
//        return hash;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//
//        if (obj.getClass() != this.getClass()) {
//            return false;
//        }
//
//        final Credits other = (Credits) obj;
//
//        if (!Objects.equals(this.id, other.id)) {
//            return false;
//        }
//
//        return true;
//    }

}
