package com.example.gestion_evenements.observer;
// Fichier: src/main/java/com/example/gestion_evenements/observer/ParticipantObserver.java

/**
 * Interface pour les observateurs (participants qui reçoivent des notifications).
 */
public interface ParticipantObserver {
    void update(String message);
}
