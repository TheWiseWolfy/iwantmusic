package com.unwise.playlist_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSong {
    private Long songId;
    private String songName;
    private String songLink;


}
