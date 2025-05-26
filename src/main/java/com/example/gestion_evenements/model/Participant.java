package com.example.gestion_evenements.model;

import com.example.gestion_evenements.exception.CapaciteMaxAtteinteException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Participant {
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private Boolean actif;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreation;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateModification;
    private List<Evenement> evenementsInscrits;
    private List<String> notifications;

    // Constructeurs
    public Participant() {
        this.id = UUID.randomUUID().toString();
        this.actif = true;
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
        this.evenementsInscrits = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public Participant(String nom, String email) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.email = validateEmail(email);
        this.actif = true;
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
        this.evenementsInscrits = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.id = id;
            updateModification();
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom != null && !nom.trim().isEmpty()) {
            this.nom = nom;
            updateModification();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
        updateModification();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
        updateModification();
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif != null ? actif : true;
        updateModification();
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public List<Evenement> getEvenementsInscrits() {
        return new ArrayList<>(evenementsInscrits);
    }

    public void setEvenementsInscrits(List<Evenement> evenementsInscrits) {
        this.evenementsInscrits = evenementsInscrits != null ? new ArrayList<>(evenementsInscrits) : new ArrayList<>();
        updateModification();
    }

    // Observer Pattern: Receive notifications
    public void recevoirNotification(String message) {
        notifications.add(message);
        System.out.println("Notification pour " + nom + " (" + email + ") : " + message);
    }

    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    // Méthode pour s'inscrire à un événement
    public void inscrireAEvenement(Evenement e) throws CapaciteMaxAtteinteException {
        if (e != null && !evenementsInscrits.contains(e)) {
            e.ajouterParticipant(this);
            evenementsInscrits.add(e);
            updateModification();
        }
    }

    // Afficher les événements
    public void afficherEvenements() {
        System.out.println("Événements de " + nom + " :");
        if (evenementsInscrits.isEmpty()) {
            System.out.println("- Aucun événement inscrit.");
        } else {
            for (Evenement e : evenementsInscrits) {
                System.out.println("- " + e.getNom());
            }
        }
    }

    // Mise à jour de la date de modification
    private void updateModification() {
        this.dateModification = LocalDateTime.now();
    }

    // Validation de l'email
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide : " + email);
        }
        return email;
    }
}