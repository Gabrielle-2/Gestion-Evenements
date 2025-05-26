package com.example.gestion_evenements.exception;

public class EvenementInexistantException extends RuntimeException {
    public EvenementInexistantException(String message) {
        super(message);
    }
}
