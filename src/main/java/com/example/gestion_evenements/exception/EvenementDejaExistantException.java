package com.example.gestion_evenements.exception;

public class EvenementDejaExistantException extends RuntimeException {
    public EvenementDejaExistantException(String message) {
        super(message);
    }
}