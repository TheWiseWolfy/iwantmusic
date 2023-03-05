package com.unwise.content_service.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(Long id) {
        super("Could not find content with ID: " + id);
    }
}
