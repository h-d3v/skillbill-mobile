package com.jde.skillbill.domaine.entites;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Utilisateur implements Serializable {
    @SerializedName("Nom")
    private String nom;
    @SerializedName("MotDePasse")
    private String motPasse;

    private String uriPhoto;
    @SerializedName("Courriel")
    private String courriel;
    private String numeroTelephone;
    @SerializedName("Monnaie")
    private Monnaie monnaieUsuelle;

    public Utilisateur(String nom, String courriel, String motPasse, Monnaie monnaie) {
        this.nom = nom;
        this.courriel = courriel;
        this.motPasse=motPasse;
        this.monnaieUsuelle=monnaie;
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

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
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
