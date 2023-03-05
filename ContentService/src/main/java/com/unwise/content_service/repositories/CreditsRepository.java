package com.unwise.content_service.repositories;


import com.unwise.content_service.embeddable.CreditKey;
import com.unwise.content_service.entities.Credits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditsRepository extends JpaRepository<Credits, CreditKey> {

    //List<Credits> findByContentID(Long id);
}
