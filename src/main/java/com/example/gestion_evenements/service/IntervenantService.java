package com.example.gestion_evenements.service;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.Conference;
import com.example.gestion_evenements.model.Intervenant;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.repository.EvenementRepositoryJson;
import com.example.gestion_evenements.repository.IntervenantRepositoryJson;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service pour gérer les opérations sur les intervenants.
 */
public class IntervenantService {
    private final IntervenantRepositoryJson intervenantRepository;
    private final EvenementRepositoryJson evenementRepository;

    public IntervenantService() {
        this.intervenantRepository = new IntervenantRepositoryJson();
        this.evenementRepository = new EvenementRepositoryJson();
    }

    public SuccessResponse ajouterIntervenant(String nom, String specialite) throws EvenementDejaExistantException {
        validateIntervenantFields(nom, specialite);
        Intervenant intervenant = new Intervenant(UUID.randomUUID().toString(), nom, specialite);
        intervenantRepository.save(intervenant);
        return new SuccessResponse("Intervenant ajouté avec succès", intervenant);
    }

    public SuccessResponse updateIntervenant(String id, String nom, String specialite) {
        Intervenant intervenant = intervenantRepository.findById(id).orElse(null);
        if (intervenant == null) {
            return new SuccessResponse("Intervenant non trouvé", null);
        }
        if (nom != null && !nom.trim().isEmpty()) {
            intervenant.setNom(nom);
        }
        if (specialite != null && !specialite.trim().isEmpty()) {
            intervenant.setSpecialite(specialite);
        }
        intervenantRepository.save(intervenant);
        return new SuccessResponse("Intervenant mis à jour avec succès", intervenant);
    }

    public SuccessResponse supprimerIntervenant(String id) {
        Intervenant intervenant = intervenantRepository.findById(id).orElse(null);
        if (intervenant == null) {
            return new SuccessResponse("Intervenant non trouvé", null);
        }
        // Note: Remove from conferences if Conference model supports intervenants
        intervenantRepository.delete(id);
        return new SuccessResponse("Intervenant supprimé avec succès", null);
    }

    public SuccessResponse associerIntervenantAConference(String intervenantId, String conferenceId) {
        Intervenant intervenant = intervenantRepository.findById(intervenantId).orElse(null);
        Conference conference = (Conference) evenementRepository.findById(conferenceId).orElse(null);
        if (intervenant == null || conference == null || !(conference instanceof Conference)) {
            return new SuccessResponse("Intervenant ou conférence non trouvé", null);
        }
        conference.ajouterIntervenant(intervenant);
        evenementRepository.save(conference);
        return new SuccessResponse("Intervenant associé à la conférence avec succès", conference);
    }

    public SuccessResponse rechercherIntervenant(String id) {
        Intervenant intervenant = intervenantRepository.findById(id).orElse(null);
        return new SuccessResponse(intervenant != null ? "Intervenant trouvé" : "Intervenant non trouvé", intervenant);
    }

    public SuccessResponse rechercherIntervenantsParSpecialite(String specialite) {
        List<Intervenant> intervenants = intervenantRepository.findBySpecialite(specialite);
        return new SuccessResponse("Intervenants trouvés pour la spécialité", intervenants);
    }

    public SuccessResponse getAllIntervenants() {
        Map<String, Intervenant> intervenants = intervenantRepository.findAll();
        return new SuccessResponse("Tous les intervenants récupérés", intervenants.values());
    }

    private void validateIntervenantFields(String nom, String specialite) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (specialite == null || specialite.trim().isEmpty()) {
            throw new IllegalArgumentException("La spécialité ne peut pas être vide.");
        }
    }
}