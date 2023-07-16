package com.Estructura.API.exception;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(String message){
        super(message);
    }
}
