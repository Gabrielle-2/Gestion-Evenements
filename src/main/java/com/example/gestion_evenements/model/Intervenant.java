package com.example.gestion_evenements.model;
// Fichier : src/main/java/com/example/gestion_evenements/model/Intervenant.java

public class Intervenant {

    private String id;
    private String nom;
    private String specialite;

    // 🔨 Constructeurs
    public Intervenant() {
    }

    public Intervenant(String id, String nom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
    }

    // ✅ Getters & Setters
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    // 📄 Méthode utilitaire d'affichage
    public void afficherDetails() {
        System.out.println("Intervenant : " + nom + " | Spécialité : " + specialite);
    }

    // 🧾 Surcharge toString (pour affichage dans des listes)
    @Override
    public String toString() {
        return nom + " (" + specialite + ")";
    }
}
