// Fichier : src/main/java/com/example/gestion_evenements/model/Conference.java
package com.example.gestion_evenements.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conference extends Evenement {

    private String theme;
    private List<Intervenant> intervenants = new ArrayList<>();

    // 🔨 Constructeurs
    public Conference() {
        super();
    }

    public Conference(String nom, LocalDateTime date, String lieu, int capaciteMax, Organisateur organisateur, String theme, List<Intervenant> intervenants) {
        super(nom, date, lieu, capaciteMax, organisateur);
        this.theme = theme;
        this.intervenants = intervenants != null ? intervenants : new ArrayList<>();
    }
    // ✅ Getters et Setters
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Intervenant> getIntervenants() {
        return intervenants;
    }

    public void setIntervenants(List<Intervenant> intervenants) {
        this.intervenants = intervenants;
    }

    // 📄 Affichage des détails
    // Implémentation de la méthode abstraite
    @Override
    public void afficherDetails() {
        System.out.println("Conférence : " + getNom());
        System.out.println("ID : " + getId());
        System.out.println("Date : " + getDate());
        System.out.println("Lieu : " + getLieu());
        System.out.println("Capacité max : " + getCapaciteMax());
        System.out.println("Thème : " + theme);
        System.out.println("Intervenants : " + intervenants);
        System.out.println("Participants : " + getParticipants().size());
    }

    // Méthodes spécifiques
    public void ajouterIntervenant(Intervenant intervenant) {
        if (!intervenants.contains(intervenant)) {
            intervenants.add(intervenant);
        }
    }
}
