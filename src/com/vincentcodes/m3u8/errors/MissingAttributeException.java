package com.vincentcodes.m3u8.errors;

public class MissingAttributeException extends RuntimeException {

    public MissingAttributeException() {
    }

    public MissingAttributeException(String message) {
        super(message);
    }
    
}