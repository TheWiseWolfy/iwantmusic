package com.unwise.playlist_service.controllers;

import com.unwise.playlist_service.models.Playlist;
import com.unwise.playlist_service.models.PlaylistSong;
import com.unwise.playlist_service.services.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//       logger.trace("A TRACE Message");
//        logger.debug("A DEBUG Message");
//        logger.info("An INFO Message");
//        logger.warn("A WARN Message");
//        logger.error("An ERROR Message");

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    //Create
    @PostMapping(value= "/playlists" ,consumes = "application/json", produces = "application/json")
    ResponseEntity<String> add(@RequestBody Playlist newPlaylist) {

        String id = playlistService.createNewPlaylist(newPlaylist).getId();
        return ResponseEntity.created(URI.create("/contents/" + id )).build();
    }

    //Read
    @GetMapping("/playlists")
    ResponseEntity<List<EntityModel<Playlist>>>  all() {

        var contentList =  playlistService.getAllPlaylists();
        var contentDTOList = contentList.stream()
                .map(EntityModel::of)
                .map(a ->  a.add(linkTo(methodOn(PlaylistController.class).one( a.getContent().getId() )).withSelfRel() )
                            .add(linkTo(methodOn(PlaylistController.class).all()).withRel("parent"))
                )
                .collect(Collectors.toList());

        //Mereu returnam 200
        return new ResponseEntity<>(contentDTOList, HttpStatus.OK );
    }

    //Read 1
    @GetMapping("/playlists/{id}")
    ResponseEntity<EntityModel<Playlist>> one(@PathVariable String id) {

        Optional<Playlist> playlist = playlistService.getPlaylistById(id);

        if(playlist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            var entityModel = EntityModel.of(playlist.get());
            entityModel.add(linkTo(methodOn(PlaylistController.class).one( playlist.get().getId() )).withSelfRel());
            entityModel.add(linkTo(methodOn(PlaylistController.class).all()).withRel("parent"));

            return new ResponseEntity<>(entityModel, HttpStatus.OK );
        }
    }

    //Put
    @PutMapping(value= "/playlists/{id}",consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> addOrUpdateContent(@RequestBody Playlist playlist, @PathVariable String id) {

        Optional<Playlist> existingPlaylist = playlistService.getPlaylistById(id);

        playlist.setId(id);
        playlistService.savePlaylist(playlist);

        if (existingPlaylist.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.created(URI.create("/contents/" + id.toString())).build();
        }
    }
    //Delete by id
    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<Void> deletePlaylistById(@PathVariable String id) {

        Optional<Playlist> existingContent = playlistService.getPlaylistById(id);

        if(existingContent.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            playlistService.deletePlaylist(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Legacy fuctionality
    @PutMapping(value= "/playlists/{playlist_id}/songs" ,consumes = "application/json", produces = "application/json")
    ResponseEntity<Playlist> putSongInList(@RequestBody List<PlaylistSong> newSongs, @PathVariable String playlist_id) {
        try {
            var response =  new ResponseEntity<>(playlistService.addSongsToPlaylist(playlist_id, newSongs), HttpStatus.OK) ;

            logger.info("We managed to add a song to a playlist.");

            return response;
        }catch (Exception e){
            logger.error("We received a bad request for adding a song to a playlist.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
