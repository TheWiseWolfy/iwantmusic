package com.unwise.playlist_service.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {

    @JsonProperty("content_id")
    Long content_id;

    @JsonProperty("content_name")
    String content_name;

    @JsonProperty("content_type")
    ContentType content_type;

    @JsonProperty("_links")
    private Links links;

    @Getter @Setter @NoArgsConstructor @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        @JsonProperty("self")
        private Self self;

        @Getter @Setter @NoArgsConstructor @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Self {
            @JsonProperty("href")
            private String href;
        }
    }

}
