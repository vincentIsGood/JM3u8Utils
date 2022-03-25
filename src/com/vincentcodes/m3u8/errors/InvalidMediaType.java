package com.vincentcodes.m3u8.errors;

public class InvalidMediaType extends RuntimeException {

    public InvalidMediaType() {
    }

    public InvalidMediaType(String message) {
        super(message);
    }
    
}
