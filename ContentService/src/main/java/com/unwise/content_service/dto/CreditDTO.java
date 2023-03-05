package com.unwise.content_service.dto;

import com.unwise.content_service.embeddable.CreditKey;
import com.unwise.content_service.entities.Artist;
import com.unwise.content_service.entities.Content;
import com.unwise.content_service.entities.TagType;
import com.unwise.content_service.misc.ContentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString
public class CreditDTO extends EntityModel<CreditDTO> {

    private CreditKey id;
    private Artist artist;
    private int artist_index;

}
