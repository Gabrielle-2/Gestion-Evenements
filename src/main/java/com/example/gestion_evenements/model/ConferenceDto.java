package com.example.gestion_evenements.model; // ou dto

import java.time.LocalDateTime;

public class ConferenceDto {
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private String theme;
    private String organisateurId; // Important!

    // Constructeurs, Getters, Setters
    // ... (ajoute-les)

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public String getOrganisateurId() { return organisateurId; }
    public void setOrganisateurId(String organisateurId) { this.organisateurId = organisateurId; }
}