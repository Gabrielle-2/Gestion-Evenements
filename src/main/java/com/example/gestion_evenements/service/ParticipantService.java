package com.example.gestion_evenements.service;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.Participant;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.repository.ParticipantRepositoryJson;

import java.util.List;
import java.util.Map;

/**
 * Service pour gérer les opérations sur les participants.
 */
public class ParticipantService {
    private final ParticipantRepositoryJson participantRepository;

    public ParticipantService() {
        this.participantRepository = new ParticipantRepositoryJson();
    }

    public SuccessResponse ajouterParticipant(String nom, String email) throws EvenementDejaExistantException {
        validateParticipantFields(nom, email);
        Participant participant = new Participant(nom, email);
        participantRepository.save(participant);
        return new SuccessResponse("Participant ajouté avec succès", participant);
    }

    public SuccessResponse updateParticipant(String id, String nom, String email, String telephone, Boolean actif) {
        Participant participant = participantRepository.findById(id).orElse(null);
        if (participant == null) {
            return new SuccessResponse("Participant non trouvé", null);
        }
        if (nom != null && !nom.trim().isEmpty()) {
            participant.setNom(nom);
        }
        if (email != null && !email.trim().isEmpty()) {
            participant.setEmail(email);
        }
        if (telephone != null) {
            participant.setTelephone(telephone);
        }
        if (actif != null) {
            participant.setActif(actif);
        }
        participantRepository.save(participant);
        return new SuccessResponse("Participant mis à jour avec succès", participant);
    }

    public SuccessResponse rechercherParticipant(String id) {
        Participant participant = participantRepository.findById(id).orElse(null);
        return new SuccessResponse(participant != null ? "Participant trouvé" : "Participant non trouvé", participant);
    }

    public SuccessResponse rechercherParticipantsParEvenement(String evenementId) {
        List<Participant> participants = participantRepository.findByEvenementId(evenementId);
        return new SuccessResponse("Participants trouvés pour l'événement", participants);
    }

    public SuccessResponse rechercherParticipantsActifs() {
        List<Participant> participants = participantRepository.findByActifTrue();
        return new SuccessResponse("Participants actifs récupérés", participants);
    }

    public SuccessResponse getAllParticipants() {
        Map<String, Participant> participants = participantRepository.findAll();
        return new SuccessResponse("Tous les participants récupérés", participants.values());
    }

    private void validateParticipantFields(String nom, String email) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide : " + email);
        }
    }
}