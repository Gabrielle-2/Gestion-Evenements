package com.example.gestion_evenements.model;

import com.example.gestion_evenements.exception.CapaciteMaxAtteinteException;
import com.example.gestion_evenements.observer.EvenementObservable;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Conference.class, name = "conference"),
        @JsonSubTypes.Type(value = Concert.class, name = "concert")
})
public abstract class Evenement implements EvenementObservable {
    private String id;
    private String nom;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;
    private String lieu;
    private int capaciteMax;
    private boolean estAnnule;
    private List<Participant> participants;
    private Organisateur organisateur;
    private List<Participant> observateurs;

    // Constructeurs
    public Evenement() {
        this.id = UUID.randomUUID().toString();
        this.participants = new ArrayList<>();
        this.observateurs = new ArrayList<>();
        this.estAnnule = false;
    }

    public Evenement(String nom, LocalDateTime date, String lieu, int capaciteMax, Organisateur organisateur) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.participants = new ArrayList<>();
        this.observateurs = new ArrayList<>();
        this.estAnnule = false;
        this.organisateur = organisateur;
    }

    public Evenement(String id, String nom, LocalDateTime date, String lieu, int capaciteMax) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
    }

    // Getters / Setters
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

    public boolean isEstAnnule() {
        return estAnnule;
    }

    public void setEstAnnule(boolean estAnnule) {
        this.estAnnule = estAnnule;
        if (estAnnule) {
            notifierObservateurs("L'événement " + nom + " a été annulé.");
        }
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    // Méthodes concrètes
    public void ajouterParticipant(Participant participant) throws CapaciteMaxAtteinteException {
        if (participants.size() >= capaciteMax) {
            throw new CapaciteMaxAtteinteException("Capacité maximale atteinte pour l'événement : " + nom);
        }
        if (!participants.contains(participant)) {
            participants.add(participant);
            ajouterObservateur(participant);
            notifierObservateurs("Nouveau participant ajouté à l'événement : " + participant.getNom());
        }
    }

    public void annuler() {
        setEstAnnule(true);
        participants.clear();
    }

    // Implémentation de EvenementObservable
    @Override
    public void ajouterObservateur(Participant participant) {
        if (!observateurs.contains(participant)) {
            observateurs.add(participant);
        }
    }

    @Override
    public void supprimerObservateur(Participant participant) {
        observateurs.remove(participant);
    }

    @Override
    public void notifierObservateurs(String message) {
        for (Participant observateur : observateurs) {
            observateur.recevoirNotification(message);
        }
    }

    // Affichage à spécialiser
    public abstract void afficherDetails();
}