package com.bartek.NetworkingPlatform.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}