package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.model.Intervenant;
import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository JSON pour gérer les intervenants.
 */
public class IntervenantRepositoryJson implements Repository<Intervenant> {
    private Map<String, Intervenant> intervenants;
    private final ObjectMapper objectMapper;
    private final String filePath = "intervenants.json";

    public IntervenantRepositoryJson() {
        this.intervenants = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void save(Intervenant intervenant) throws EvenementDejaExistantException {
        if (intervenants.containsKey(intervenant.getId())) {
            throw new EvenementDejaExistantException("L'intervenant avec l'ID " + intervenant.getId() + " existe déjà.");
        }
        intervenants.put(intervenant.getId(), intervenant);
    }

    @Override
    public Optional<Intervenant> findById(String id) {
        return Optional.ofNullable(intervenants.get(id));
    }

    @Override
    public void delete(String id) {
        intervenants.remove(id);
    }

    @Override
    public Map<String, Intervenant> findAll() {
        return new HashMap<>(intervenants);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), intervenants);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            intervenants = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Intervenant.class));
        } else {
            intervenants = new HashMap<>();
        }
    }

    public List<Intervenant> findBySpecialite(String specialite) {
        return intervenants.values().stream()
                .filter(i -> i.getSpecialite() != null && i.getSpecialite().toLowerCase().contains(specialite.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Intervenant> findByNomContainingIgnoreCase(String nom) {
        return intervenants.values().stream()
                .filter(i -> i.getNom() != null && i.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }
}