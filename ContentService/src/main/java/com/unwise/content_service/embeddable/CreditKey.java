package com.unwise.content_service.embeddable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class CreditKey implements Serializable {

    Long contentID;
    String artistID;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}