package com.nasr.springsecurityclient.exception;

public class TokenNotValidException extends RuntimeException {

    public TokenNotValidException(String message) {
        super(message);
    }
}
