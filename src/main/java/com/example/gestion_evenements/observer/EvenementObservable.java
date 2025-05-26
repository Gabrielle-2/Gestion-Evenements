package com.example.gestion_evenements.observer;

import com.example.gestion_evenements.model.Participant;

/**
 * Interface pour les événements observables, permettant d'ajouter/retirer des observateurs (participants).
 */
public interface EvenementObservable {
    void ajouterObservateur(Participant participant);
    void supprimerObservateur(Participant participant);
    void notifierObservateurs(String message);
}