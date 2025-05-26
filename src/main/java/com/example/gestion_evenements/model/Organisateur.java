// Fichier : src/main/java/com/example/gestion_evenements/model/Organisateur.java
package com.example.gestion_evenements.model;

import java.util.ArrayList;
import java.util.List;

public class Organisateur extends Participant {

    private List<Evenement> evenementsOrganises = new ArrayList<>();

    // 🔨 Constructeurs
    public Organisateur() {
        super();
    }


    // ✅ Getters & Setters
    public List<Evenement> getEvenementsOrganises() {
        return evenementsOrganises;
    }

    public void setEvenementsOrganises(List<Evenement> evenementsOrganises) {
        this.evenementsOrganises = evenementsOrganises;
    }

    // ➕ Ajouter un événement organisé
    public void ajouterEvenement(Evenement e) {
        evenementsOrganises.add(e);
    }

    // 📄 Affichage utile
    public void afficherEvenementsOrganises() {
        System.out.println("Événements organisés par " + getNom() + " :");
        if (evenementsOrganises.isEmpty()) {
            System.out.println("- Aucun événement pour le moment.");
        } else {
            for (Evenement e : evenementsOrganises) {
                System.out.println("- " + e.getNom());
            }
        }
    }
}
