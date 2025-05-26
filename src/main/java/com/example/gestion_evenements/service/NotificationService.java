package com.example.gestion_evenements.service;

import com.example.gestion_evenements.model.Evenement;
import com.example.gestion_evenements.model.Participant;
import org.springframework.stereotype.Service;

// Service de notification
@Service
public class NotificationService {

    // Simuler l'envoi de notification d'inscription
    public void notifierInscription(Participant participant, Evenement evenement) {
        String message = String.format("Bonjour %s, vous êtes inscrit(e) à l'événement '%s' le %s",
                participant.getNom(), evenement.getNom(), evenement.getDate());
        System.out.println("EMAIL envoyé à " + participant.getEmail() + ": " + message);
    }

    // Simuler l'envoi de notification d'annulation
    public void notifierAnnulationEvenement(Evenement evenement) {
        evenement.getParticipants().forEach(participant -> {
            String message = String.format("Bonjour %s, l'événement '%s' a été annulé",
                    participant.getNom(), evenement.getNom());
            System.out.println("EMAIL envoyé à " + participant.getEmail() + ": " + message);
        });
    }

    // Simuler l'envoi de notification de modification
    public void notifierModificationEvenement(Evenement evenement) {
        evenement.getParticipants().forEach(participant -> {
            String message = String.format("Bonjour %s, l'événement '%s' a été modifié",
                    participant.getNom(), evenement.getNom());
            System.out.println("EMAIL envoyé à " + participant.getEmail() + ": " + message);
        });
    }
}

