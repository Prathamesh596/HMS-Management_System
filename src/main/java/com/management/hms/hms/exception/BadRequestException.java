package com.management.hms.hms.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}
