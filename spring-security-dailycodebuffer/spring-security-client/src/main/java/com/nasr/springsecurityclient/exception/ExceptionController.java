package com.nasr.springsecurityclient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = TokenNotValidException.class)
    public ResponseEntity<String> tokenNotValidException(TokenNotValidException e){
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}
