package com.unwise.iwantmusic.repositories;


import com.unwise.iwantmusic.entities.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

}
