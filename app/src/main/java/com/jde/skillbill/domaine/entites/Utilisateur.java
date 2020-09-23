package com.jde.skillbill.domaine.entites;


import java.util.Objects;

public class Utilisateur {

    private String prenom;
    private String nom;
    private String motPasse;
    private String uriPhoto;
    private String courriel;
    private int numeroTelephone;
    private Monnaie monnaieUsuelle;

    //On va utiliser le constructeur avec seulement l'attribut nom qui inclure aussi le prenom
    // pour des raisons de logistiques et paresse
    public Utilisateur(String prenom, String nom, String courriel, String motPasse) {
        this.prenom = prenom;
        this.nom = nom;
        this.courriel = courriel;
    }

    public Utilisateur(String nom, String courriel, String motPasse) {
        this.nom = nom;
        this.courriel = courriel;
        this.motPasse=motPasse;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public int getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(int numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public Monnaie getMonnaieUsuelle() {
        return monnaieUsuelle;
    }

    public void setMonnaieUsuelle(Monnaie monnaieUsuelle) {
        this.monnaieUsuelle = monnaieUsuelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return courriel.equals(that.courriel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courriel);
    }
}
