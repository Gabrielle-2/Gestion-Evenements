package com.example.gestion_evenements.service;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.Organisateur;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.repository.EvenementRepositoryJson;
import com.example.gestion_evenements.repository.OrganisateurRepositoryJson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service pour gérer les opérations sur les organisateurs.
 */
public class OrganisateurService {
    private final OrganisateurRepositoryJson organisateurRepository;
    private final EvenementRepositoryJson evenementRepository;

    public OrganisateurService() {
        this.organisateurRepository = new OrganisateurRepositoryJson();
        this.evenementRepository = new EvenementRepositoryJson();
    }

    public SuccessResponse ajouterOrganisateur(String nom, String email) throws EvenementDejaExistantException {
        validateParticipantFields(nom, email);
        Organisateur organisateur = new Organisateur();
        organisateur.setNom(nom);
        organisateur.setEmail(email);
        organisateurRepository.save(organisateur);
        return new SuccessResponse("Organisateur ajouté avec succès", organisateur);
    }

    public SuccessResponse updateOrganisateur(String id, String nom, String email, String telephone, Boolean actif) {
        Organisateur organisateur = organisateurRepository.findById(id).orElse(null);
        if (organisateur == null) {
            return new SuccessResponse("Organisateur non trouvé", null);
        }
        if (nom != null && !nom.trim().isEmpty()) {
            organisateur.setNom(nom);
        }
        if (email != null && !email.trim().isEmpty()) {
            organisateur.setEmail(email);
        }
        if (telephone != null) {
            organisateur.setTelephone(telephone);
        }
        if (actif != null) {
            organisateur.setActif(actif);
        }
        organisateurRepository.save(organisateur);
        return new SuccessResponse("Organisateur mis à jour avec succès", organisateur);
    }

    public SuccessResponse supprimerOrganisateur(String id) {
        Organisateur organisateur = organisateurRepository.findById(id).orElse(null);
        if (organisateur == null) {
            return new SuccessResponse("Organisateur non trouvé", null);
        }
        organisateur.getEvenementsOrganises().forEach(e -> evenementRepository.delete(e.getId()));
        organisateurRepository.delete(id);
        return new SuccessResponse("Organisateur supprimé avec succès", null);
    }

    public SuccessResponse rechercherOrganisateur(String id) {
        Organisateur organisateur = organisateurRepository.findById(id).orElse(null);
        return new SuccessResponse(organisateur != null ? "Organisateur trouvé" : "Organisateur non trouvé", organisateur);
    }

    public SuccessResponse rechercherOrganisateursParEvenement(String evenementId) {
        List<Organisateur> organisateurs = organisateurRepository.findByEvenementId(evenementId);
        return new SuccessResponse("Organisateurs trouvés pour l'événement", organisateurs);
    }

    public SuccessResponse getAllOrganisateurs() {
        Map<String, Organisateur> organisateurs = organisateurRepository.findAll();
        return new SuccessResponse("Tous les organisateurs récupérés", organisateurs.values());
    }

    public void saveToFile() throws IOException {
        organisateurRepository.saveToFile();
    }

    public void loadFromFile() throws IOException {
        organisateurRepository.loadFromFile();
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