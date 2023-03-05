package com.unwise.content_service.repositories;


import com.unwise.content_service.entities.Artist;
import com.unwise.content_service.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

  /// @Query("SELECT ar.artist_identifier  FROM content con \n" +
  ///         "INNER JOIN credits cred  ON con.content_id  = cred.content_id \n" +
  ///         "INNER JOIN artists ar  ON ar.artist_identifier  = cred.artist_identifier  \n" +
  ///         "WHERE con.content_id =  :contentID")
  /// List<Artist> getArtistsByContent(@Param("contentID") Long userId);

}
