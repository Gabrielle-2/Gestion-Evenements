package com.example.gestion_evenements.util;

public interface SerializationUtil {
    public void saveToJson(Object object, String filename);
    public <T> T loadFromJson(String filename, Class<T> clazz);
    public void saveToXml(Object object, String filename);
    public <T> T loadFromXml(String filename, Class<T> clazz);
}