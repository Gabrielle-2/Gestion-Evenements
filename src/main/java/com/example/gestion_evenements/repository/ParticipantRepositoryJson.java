package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;
import com.example.gestion_evenements.model.Participant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipantRepositoryJson implements Repository<Participant> {
    private Map<String, Participant> participants;
    private final ObjectMapper objectMapper;
    private final String filePath = "participants.json";

    public ParticipantRepositoryJson() {
        this.participants = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public void save(Participant participant) throws EvenementDejaExistantException {
        if (participants.containsKey(participant.getId())) {
            throw new EvenementDejaExistantException("Le participant avec l'ID " + participant.getId() + " existe déjà.");
        }
        participants.put(participant.getId(), participant);
    }

    @Override
    public Optional<Participant> findById(String id) {
        return Optional.ofNullable(participants.get(id));
    }

    @Override
    public void delete(String id) {
        participants.remove(id);
    }

    @Override
    public Map<String, Participant> findAll() {
        return new HashMap<>(participants);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), participants);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            participants = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Participant.class));
        } else {
            participants = new HashMap<>();
        }
    }

    public List<Participant> findByEvenementId(String evenementId) {
        return participants.values().stream()
                .filter(p -> p.getEvenementsInscrits().stream().anyMatch(e -> e.getId().equals(evenementId)))
                .collect(Collectors.toList());
    }

    public List<Participant> findByActifTrue() {
        return participants.values().stream()
                .filter(Participant::isActif)
                .collect(Collectors.toList());
    }
}