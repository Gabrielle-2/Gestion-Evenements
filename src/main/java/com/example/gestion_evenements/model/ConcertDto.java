package com.example.gestion_evenements.model;

import java.time.LocalDateTime;

public class ConcertDto {
    private String id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private String artiste;
    private String genreMusical;
    private String organisateurId;  // Nouveau champ ajouté

    // Constructeur par défaut
    public ConcertDto() {
    }

    // Constructeur avec tous les champs
    public ConcertDto(String id, String nom, LocalDateTime date, String lieu, int capaciteMax,
                      String artiste, String genreMusical, String organisateurId) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.artiste = artiste;
        this.genreMusical = genreMusical;
        this.organisateurId = organisateurId;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getGenreMusical() {
        return genreMusical;
    }

    public void setGenreMusical(String genreMusical) {
        this.genreMusical = genreMusical;
    }

    public String getOrganisateurId() {
        return organisateurId;
    }

    public void setOrganisateurId(String organisateurId) {
        this.organisateurId = organisateurId;
    }
}