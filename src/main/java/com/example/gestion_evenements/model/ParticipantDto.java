package com.example.gestion_evenements.model;

public class ParticipantDto {
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private Boolean actif;
    private String organisateurId;  // Nouveau champ ajouté

    // Constructeurs
    public ParticipantDto() {}

    public ParticipantDto(String id, String nom, String email, String telephone, Boolean actif, String organisateurId) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.actif = actif;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getOrganisateurId() {  // Nouvelle méthode demandée
        return organisateurId;
    }

    public void setOrganisateurId(String organisateurId) {  // Setter correspondant
        this.organisateurId = organisateurId;
    }
}