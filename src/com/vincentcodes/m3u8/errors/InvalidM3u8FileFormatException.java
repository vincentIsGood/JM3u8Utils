package com.vincentcodes.m3u8.errors;

public class InvalidM3u8FileFormatException extends RuntimeException {

    public InvalidM3u8FileFormatException() {
    }

    public InvalidM3u8FileFormatException(String message) {
        super(message);
    }
    
}
