package com.unwise.playlist_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.unwise.playlist_service.misc.*;
import com.unwise.playlist_service.services.ContentService;
import com.unwise.playlist_service.services.PlaylistService;
import com.unwise.playlist_service.services.SOAPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class GatewayController {

    @Autowired
    SOAPService soapService;

    @Autowired
    ContentService contentService;

    @Autowired
    PlaylistService playlistService;

    Logger logger = LoggerFactory.getLogger(GatewayController.class);

    @PutMapping("/playlist/{playlist_id}")
    public ResponseEntity<?> putPlaylist(@RequestBody Playlist playlist, @PathVariable String playlist_id,
                                         @RequestHeader(value = "Authorization", required = false) String token ){

        var tokenValidation =  soapService.isValidToken(token);

        //Verificam daca exista un token si e valid
        if(tokenValidation.isPresent() && tokenValidation.get().isValid() ){
            String id = tokenValidation.get().getId();
            Integer role = Integer.valueOf( tokenValidation.get().getRole() );

            //Verificam daca exista deja un playlist cu id-ul asta
            Optional<Playlist> recoveredPlaylist =  playlistService.getPlaylistById(playlist_id);

            //Daca nu exista deja un playlist cu ID-ul asta, sau playlist-ul e detinut de persoana care face request-ul
            //atunci putem continua
            if(recoveredPlaylist.isEmpty() || Objects.equals(id, recoveredPlaylist.get().getOwner_id() )){

                //Do we need to complete any song ?
                for (int i = 0; i < playlist.getSongs().size(); i++) {
                    var song = playlist.getSongs().get(i);
                    if(song.getSongName().isEmpty() || song.getSongLink().isEmpty() ){

                        var songDetails = contentService.getContentByID(song.getSongId() );
                        if(songDetails.isPresent()){
                            playlist.getSongs().get(i).setSongName(songDetails.get().getContent_name());
                            playlist.getSongs().get(i).setSongLink(songDetails.get().getLinks().getSelf().getHref());
                        }
                    }
                }

                HttpResponse<String> response =  playlistService.putPlaylist(playlist,playlist_id );

                if(response.statusCode() == 200) {
                    return new ResponseEntity(HttpStatus.OK);
                }else if (response.statusCode() == 204){
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/playlist")
    public ResponseEntity< List<Playlist> > putPlaylist(){
        //We determine what song we need

        //We send the put request to the playlist service

        List<Playlist> playlists =  playlistService.getAllPlaylists();

        return new ResponseEntity<>(playlists,HttpStatus.OK);
    }

    @GetMapping("/playlist/{playlist_id}")
    public ResponseEntity<Playlist> putPlaylist(@PathVariable String playlist_id){
        //We determine what song we need

        //We send the put request to the playlist service

        Optional<Playlist> playlist =  playlistService.getPlaylistById(playlist_id);

        if(playlist.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(playlist.get(),HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        // Perform authentication (e.g. check against a database)

        logger.info("--Someone tried to authentificate with " + request.getUsername() + " and " + request.getPassword());

        var authenticationResults = soapService.isValidCredentials( request.getUsername(), request.getPassword() );
        if ( authenticationResults.isEmpty()  ) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        //Debug purposes
        System.out.println( soapService.isValidToken( authenticationResults.get() + "wa").isPresent()  );
        //return ResponseEntity.notFound().build();

        return new ResponseEntity<>(new AuthenticationResponse( authenticationResults.get() ), HttpStatus.OK);
    }
}
