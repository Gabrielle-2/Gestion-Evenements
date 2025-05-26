package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.model.Conference;
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
 * Repository JSON pour gérer les conférences.
 */
public class ConferenceRepositoryJson implements Repository<Conference> {
    private Map<String, Conference> conferences;
    private final ObjectMapper objectMapper;
    private final String filePath = "conferences.json";

    public ConferenceRepositoryJson() {
        this.conferences = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void save(Conference conference) throws EvenementDejaExistantException {
        if (conferences.containsKey(conference.getId())) {
            throw new EvenementDejaExistantException("La conférence avec l'ID " + conference.getId() + " existe déjà.");
        }
        conferences.put(conference.getId(), conference);
    }

    @Override
    public Optional<Conference> findById(String id) {
        return Optional.ofNullable(conferences.get(id));
    }

    @Override
    public void delete(String id) {
        conferences.remove(id);
    }

    @Override
    public Map<String, Conference> findAll() {
        return new HashMap<>(conferences);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), conferences);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            conferences = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Conference.class));
        } else {
            conferences = new HashMap<>();
        }
    }

    public List<Conference> findByThemeContainingIgnoreCase(String theme) {
        return conferences.values().stream()
                .filter(c -> c.getTheme() != null && c.getTheme().toLowerCase().contains(theme.toLowerCase()))
                .collect(Collectors.toList());
    }
}