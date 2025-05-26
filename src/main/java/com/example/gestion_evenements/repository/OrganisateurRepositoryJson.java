package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.Organisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrganisateurRepositoryJson implements Repository<Organisateur> {
    private Map<String, Organisateur> organisateurs;
    private final ObjectMapper objectMapper;
    private final String filePath = "organisateurs.json";

    public OrganisateurRepositoryJson() {
        this.organisateurs = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public void save(Organisateur organisateur) throws EvenementDejaExistantException {
        if (organisateurs.containsKey(organisateur.getId())) {
            throw new EvenementDejaExistantException("L'organisateur avec l'ID " + organisateur.getId() + " existe déjà.");
        }
        organisateurs.put(organisateur.getId(), organisateur);
    }

    @Override
    public Optional<Organisateur> findById(String id) {
        return Optional.ofNullable(organisateurs.get(id));
    }

    @Override
    public void delete(String id) {
        organisateurs.remove(id);
    }

    @Override
    public Map<String, Organisateur> findAll() {
        return new HashMap<>(organisateurs);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), organisateurs);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            organisateurs = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Organisateur.class));
        } else {
            organisateurs = new HashMap<>();
        }
    }

    public List<Organisateur> findByEvenementId(String evenementId) {
        return organisateurs.values().stream()
                .filter(o -> o.getEvenementsOrganises().stream().anyMatch(e -> e.getId().equals(evenementId)))
                .collect(Collectors.toList());
    }
}