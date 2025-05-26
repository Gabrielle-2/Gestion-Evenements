package com.example.gestion_evenements.exception;

// Exception pour capacité maximale atteinte
public class CapaciteMaxAtteinteException extends Exception {
    public CapaciteMaxAtteinteException(String message) {
        super(message);
    }

    public CapaciteMaxAtteinteException(String nomEvenement, int capaciteMax) {
        super("L'événement '" + nomEvenement + "' a atteint sa capacité maximale de " + capaciteMax + " participants.");
    }
}