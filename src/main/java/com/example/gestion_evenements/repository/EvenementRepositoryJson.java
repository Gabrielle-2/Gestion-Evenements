package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.model.Evenement;
import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EvenementRepositoryJson implements Repository<Evenement> {
    private Map<String, Evenement> evenements;
    private final ObjectMapper objectMapper;
    private final String filePath = "evenements.json";

    public EvenementRepositoryJson() {
        this.evenements = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public void save(Evenement evenement) throws EvenementDejaExistantException {
        if (evenements.containsKey(evenement.getId())) {
            throw new EvenementDejaExistantException("L'événement avec l'ID " + evenement.getId() + " existe déjà.");
        }
        evenements.put(evenement.getId(), evenement);
    }

    @Override
    public Optional<Evenement> findById(String id) {
        return Optional.ofNullable(evenements.get(id));
    }

    @Override
    public void delete(String id) {
        evenements.remove(id);
    }

    @Override
    public Map<String, Evenement> findAll() {
        return new HashMap<>(evenements);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), evenements);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            evenements = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Evenement.class));
        } else {
            evenements = new HashMap<>();
        }
    }

    public List<Evenement> findEvenementsAVenir(LocalDateTime now) {
        return evenements.values().stream()
                .filter(e -> !e.isEstAnnule() && e.getDate() != null && e.getDate().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Evenement> findByLieuContainingIgnoreCase(String lieu) {
        return evenements.values().stream()
                .filter(e -> e.getLieu() != null && e.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .collect(Collectors.toList());
    }
}