package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.model.Concert;
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
 * Repository JSON pour gérer les concerts.
 */
public class ConcertRepositoryJson implements Repository<Concert> {
    private Map<String, Concert> concerts;
    private final ObjectMapper objectMapper;
    private final String filePath = "concerts.json";

    public ConcertRepositoryJson() {
        this.concerts = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void save(Concert concert) throws EvenementDejaExistantException {
        if (concerts.containsKey(concert.getId())) {
            throw new EvenementDejaExistantException("Le concert avec l'ID " + concert.getId() + " existe déjà.");
        }
        concerts.put(concert.getId(), concert);
    }

    @Override
    public Optional<Concert> findById(String id) {
        return Optional.ofNullable(concerts.get(id));
    }

    @Override
    public void delete(String id) {
        concerts.remove(id);
    }

    @Override
    public Map<String, Concert> findAll() {
        return new HashMap<>(concerts);
    }

    @Override
    public void saveToFile() throws IOException {
        objectMapper.writeValue(new File(filePath), concerts);
    }

    @Override
    public void loadFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            concerts = objectMapper.readValue(file, objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, String.class, Concert.class));
        } else {
            concerts = new HashMap<>();
        }
    }

    public List<Concert> findByArtisteContainingIgnoreCase(String artiste) {
        return concerts.values().stream()
                .filter(c -> c.getArtiste() != null && c.getArtiste().toLowerCase().contains(artiste.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Concert> findByGenreMusicalContainingIgnoreCase(String genreMusical) {
        return concerts.values().stream()
                .filter(c -> c.getGenreMusical() != null && c.getGenreMusical().toLowerCase().contains(genreMusical.toLowerCase()))
                .collect(Collectors.toList());
    }
}