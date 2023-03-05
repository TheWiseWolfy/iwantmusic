package com.unwise.playlist_service.dto;

import com.unwise.playlist_service.misc.ContentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString
public class ContentDTO {

    private  Long content_id;
    private String content_name;
    private LocalDate content_date;
    private ContentType content_type;

}
