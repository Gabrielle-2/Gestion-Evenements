package com.example.gestion_evenements.repository;

import com.example.gestion_evenements.exception.EvenementDejaExistantException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Interface générique pour les repositories JSON.
 */
public interface Repository<T> {
    void save(T entity) throws EvenementDejaExistantException;
    Optional<T> findById(String id);
    void delete(String id);
    Map<String, T> findAll();
    void saveToFile() throws IOException;
    void loadFromFile() throws IOException;
}