package com.example.gestion_evenements.service;

import com.example.gestion_evenements.repository.EvenementRepositoryJson;
import com.example.gestion_evenements.repository.IntervenantRepositoryJson;
import com.example.gestion_evenements.repository.OrganisateurRepositoryJson;
import com.example.gestion_evenements.repository.ParticipantRepositoryJson;

import java.io.IOException;

/**
 * Service pour gérer la persistance des données.
 */
public class PersistenceService {
    private final EvenementRepositoryJson evenementRepository;
    private final ParticipantRepositoryJson participantRepository;
    private final OrganisateurRepositoryJson organisateurRepository;
    private final IntervenantRepositoryJson intervenantRepository;

    public PersistenceService() {
        this.evenementRepository = new EvenementRepositoryJson();
        this.participantRepository = new ParticipantRepositoryJson();
        this.organisateurRepository = new OrganisateurRepositoryJson();
        this.intervenantRepository = new IntervenantRepositoryJson();
    }

    public void saveToFile() throws IOException {
        evenementRepository.saveToFile();
        participantRepository.saveToFile();
        organisateurRepository.saveToFile();
        intervenantRepository.saveToFile();
    }

    public void loadFromFile() throws IOException {
        evenementRepository.loadFromFile();
        participantRepository.loadFromFile();
        organisateurRepository.loadFromFile();
        intervenantRepository.loadFromFile();
    }
}