package com.example.gestion_evenements.service;

import com.example.gestion_evenements.exception.CapaciteMaxAtteinteException;
import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.*;
import com.example.gestion_evenements.repository.EvenementRepositoryJson;
import com.example.gestion_evenements.repository.OrganisateurRepositoryJson;
import com.example.gestion_evenements.repository.ParticipantRepositoryJson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service pour gérer les opérations sur les événements, participants et organisateurs.
 */
@Service
public class EvenementService {
    private final EvenementRepositoryJson evenementRepository;
    private final OrganisateurRepositoryJson organisateurRepository;
    private final ParticipantRepositoryJson participantRepository;

    public EvenementService() {
        this.evenementRepository = new EvenementRepositoryJson();
        this.organisateurRepository = new OrganisateurRepositoryJson();
        this.participantRepository = new ParticipantRepositoryJson();
    }

    // Méthodes pour les événements
    public SuccessResponse ajouterConference(String nom, LocalDateTime date, String lieu, int capaciteMax, String theme, Organisateur organisateur) throws EvenementDejaExistantException {
        validateCommonFields(nom, date, lieu, capaciteMax, organisateur);
        Conference conference = new Conference();
        conference.setId(UUID.randomUUID().toString());
        conference.setNom(nom);
        conference.setDate(date);
        conference.setLieu(lieu);
        conference.setCapaciteMax(capaciteMax);
        conference.setOrganisateur(organisateur);
        conference.setTheme(theme);
        conference.setIntervenants(new ArrayList<>());
        organisateur.ajouterEvenement(conference);
        evenementRepository.save(conference);
        organisateurRepository.save(organisateur);
        return new SuccessResponse("Conférence ajoutée avec succès", conference);
    }

    public SuccessResponse ajouterConcert(String nom, LocalDateTime date, String lieu, int capaciteMax, String artiste, String genreMusical, Organisateur organisateur) throws EvenementDejaExistantException {
        validateCommonFields(nom, date, lieu, capaciteMax, organisateur);
        Concert concert = new Concert();
        concert.setId(UUID.randomUUID().toString());
        concert.setNom(nom);
        concert.setDate(date);
        concert.setLieu(lieu);
        concert.setCapaciteMax(capaciteMax);
        concert.setOrganisateur(organisateur);
        concert.setArtiste(artiste);
        concert.setGenreMusical(genreMusical);
        organisateur.ajouterEvenement(concert);
        evenementRepository.save(concert);
        organisateurRepository.save(organisateur);
        return new SuccessResponse("Concert ajouté avec succès", concert);
    }

    public SuccessResponse supprimerEvenement(String id) {
        Evenement evenement = evenementRepository.findById(id).orElse(null);
        if (evenement == null) {
            return new SuccessResponse("Événement non trouvé", null);
        }
        if (evenement.getOrganisateur() != null) {
            Organisateur organisateur = evenement.getOrganisateur();
            organisateur.getEvenementsOrganises().remove(evenement);
            organisateurRepository.save(organisateur);
        }
        evenementRepository.delete(id);
        return new SuccessResponse("Événement supprimé avec succès", null);
    }

    public SuccessResponse rechercherEvenement(String id) {
        Evenement evenement = evenementRepository.findById(id).orElse(null);
        return new SuccessResponse(evenement != null ? "Événement trouvé" : "Événement non trouvé", evenement);
    }

    public SuccessResponse rechercherEvenementsAVenir() {
        List<Evenement> evenements = evenementRepository.findEvenementsAVenir(LocalDateTime.now());
        return new SuccessResponse("Événements à venir récupérés", evenements);
    }

    public SuccessResponse rechercherEvenementsParLieu(String lieu) {
        List<Evenement> evenements = evenementRepository.findByLieuContainingIgnoreCase(lieu);
        return new SuccessResponse("Événements trouvés pour le lieu", evenements);
    }

    public SuccessResponse inscrireParticipant(String participantId, String evenementId) throws CapaciteMaxAtteinteException {
        Participant participant = participantRepository.findById(participantId).orElse(null);
        Evenement evenement = evenementRepository.findById(evenementId).orElse(null);
        if (participant == null || evenement == null) {
            return new SuccessResponse("Participant ou événement non trouvé", null);
        }
        participant.inscrireAEvenement(evenement);
        participantRepository.save(participant);
        evenementRepository.save(evenement);
        return new SuccessResponse("Participant inscrit avec succès", participant);
    }

    public SuccessResponse getAllEvenements() {
        Map<String, Evenement> evenements = evenementRepository.findAll();
        return new SuccessResponse("Tous les événements récupérés", evenements.values());
    }

    // Méthodes pour les participants
    public void ajouterParticipant(String nom, String email) {
        // À implémenter
    }

    public Participant rechercherParticipant(String id) {
        // À implémenter
        return null;
    }

    public List<Participant> rechercherParticipantsParEvenement(String evenementId) {
        // À implémenter
        return null;
    }

    public Map<String, Participant> getAllParticipants() {
        // À implémenter
        return null;
    }

    // Méthodes pour les organisateurs
    public void ajouterOrganisateur(String nom, String email) {
        // À implémenter
    }

    public void supprimerOrganisateur(String id) {
        // À implémenter
    }

    public Organisateur rechercherOrganisateur(String id) {
        // À implémenter
        return null;
    }

    public List<Organisateur> rechercherOrganisateursParEvenement(String evenementId) {
        // À implémenter
        return null;
    }

    public Map<String, Organisateur> getAllOrganisateurs() {
        // À implémenter
        return null;
    }

    // Méthodes de gestion des données
    public void saveToFile() throws IOException {
        evenementRepository.saveToFile();
    }

    public void loadFromFile() throws IOException {
        evenementRepository.loadFromFile();
    }

    // Validation commune
    private void validateCommonFields(String nom, LocalDateTime date, String lieu, int capaciteMax, Organisateur organisateur) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'événement ne peut pas être vide.");
        }
        if (date == null || date.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date doit être dans le futur.");
        }
        if (lieu == null || lieu.trim().isEmpty()) {
            throw new IllegalArgumentException("Le lieu ne peut pas être vide.");
        }
        if (capaciteMax <= 0) {
            throw new IllegalArgumentException("La capacité maximale doit être positive.");
        }
        if (organisateur == null) {
            throw new IllegalArgumentException("L'organisateur ne peut pas être null.");
        }
    }
}