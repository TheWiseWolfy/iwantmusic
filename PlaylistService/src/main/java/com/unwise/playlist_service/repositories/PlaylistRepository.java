package com.unwise.playlist_service.repositories;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.unwise.playlist_service.models.Playlist;
import com.unwise.playlist_service.models.PlaylistSong;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String> {

    @Query("{playlist_name:'?0'}")
    Playlist findUserByName(String playlist_name);

    @Query("{'_id': ?0}, {$addToSet: {songs: ?1}}")
    Playlist addSong(String playlistID, PlaylistSong element);

    @Query("{'_id': ?0}")
    Optional<Playlist> findByIdCustom(String id);

}
