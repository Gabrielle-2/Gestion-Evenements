package com.example.gestion_evenements.model;

import com.example.gestion_evenements.exception.CapaciteMaxAtteinteException;
import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.service.EvenementService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Singleton pour gérer les opérations via le service EvenementService.
 */

public class GestionEvenements {
    private static GestionEvenements instance;
    private final EvenementService evenementService;

    private GestionEvenements() {
        this.evenementService = new EvenementService();
    }

    public static synchronized GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }

    public void ajouterConference(String nom, LocalDateTime date, String lieu, int capaciteMax, String theme, Organisateur organisateur) throws EvenementDejaExistantException {
        evenementService.ajouterConference(nom, date, lieu, capaciteMax, theme, organisateur);
    }

    public void ajouterConcert(String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical, Organisateur organisateur) throws EvenementDejaExistantException {
        evenementService.ajouterConcert(nom, date, lieu, capaciteMax, artiste, genreMusical, organisateur);
    }

    public void ajouterParticipant(String nom, String email) throws EvenementDejaExistantException {
        evenementService.ajouterParticipant(nom, email);
    }

    public void ajouterOrganisateur(String nom, String email) throws EvenementDejaExistantException {
        evenementService.ajouterOrganisateur(nom, email);
    }

    public void inscrireParticipant(String participantId, String evenementId) throws CapaciteMaxAtteinteException {
        evenementService.inscrireParticipant(participantId, evenementId);
    }

    public void supprimerEvenement(String id) {
        evenementService.supprimerEvenement(id);
    }

    public void supprimerOrganisateur(String id) {
        evenementService.supprimerOrganisateur(id);
    }

    public SuccessResponse rechercherEvenement(String id) {
        return evenementService.rechercherEvenement(id);
    }

    public Participant rechercherParticipant(String id) {
        return evenementService.rechercherParticipant(id);
    }

    public Organisateur rechercherOrganisateur(String id) {
        return evenementService.rechercherOrganisateur(id);
    }

    public List<Evenement> rechercherEvenementsAVenir() {
        return (List<Evenement>) evenementService.rechercherEvenementsAVenir();
    }

    public List<Evenement> rechercherEvenementsParLieu(String lieu) {
        return (List<Evenement>) evenementService.rechercherEvenementsParLieu(lieu);
    }

    public List<Participant> rechercherParticipantsParEvenement(String evenementId) {
        return evenementService.rechercherParticipantsParEvenement(evenementId);
    }

    public List<Organisateur> rechercherOrganisateursParEvenement(String evenementId) {
        return evenementService.rechercherOrganisateursParEvenement(evenementId);
    }

    public Map<String, Evenement> getEvenements() {
        return (Map<String, Evenement>) evenementService.getAllEvenements();
    }

    public Map<String, Participant> getParticipants() {
        return evenementService.getAllParticipants();
    }

    public Map<String, Organisateur> getOrganisateurs() {
        return evenementService.getAllOrganisateurs();
    }

    public void saveToFile() throws IOException {
        evenementService.saveToFile();
    }

    public void loadFromFile() throws IOException {
        evenementService.loadFromFile();
    }
}