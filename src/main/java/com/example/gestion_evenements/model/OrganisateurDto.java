package com.example.gestion_evenements.model;
public class OrganisateurDto {
    private String nom;
    private String email;

    // Constructeurs, Getters et Setters
    public OrganisateurDto() {}

    public OrganisateurDto(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}