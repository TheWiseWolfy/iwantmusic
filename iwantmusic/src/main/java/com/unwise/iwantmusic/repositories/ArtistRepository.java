package com.unwise.iwantmusic.repositories;


import com.unwise.iwantmusic.entities.Artist;
import com.unwise.iwantmusic.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
