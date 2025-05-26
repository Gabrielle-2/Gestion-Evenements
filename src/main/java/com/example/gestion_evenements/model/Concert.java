package com.example.gestion_evenements.model;

import java.time.LocalDateTime;
public class Concert extends Evenement {

    private String artiste;
    private String genreMusical;

    // 🔨 Constructeurs
    public Concert() {
        super();
    }

    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax,
                   String artiste, String genreMusical) {
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
    }

    // ✅ Getters et Setters
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

    // Implémentation de la méthode abstraite
    @Override
    public void afficherDetails() {
        System.out.println("Concert : " + getNom());
        System.out.println("ID : " + getId());
        System.out.println("Date : " + getDate());
        System.out.println("Lieu : " + getLieu());
        System.out.println("Capacité max : " + getCapaciteMax());
        System.out.println("Artiste : " + artiste);
        System.out.println("Genre musical : " + genreMusical);
        System.out.println("Participants : " + getParticipants().size());
    }
}
