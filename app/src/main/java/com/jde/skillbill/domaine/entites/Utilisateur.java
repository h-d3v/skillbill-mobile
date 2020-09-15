package com.jde.skillbill.domaine.entites;


public class Utilisateur {

    private String prenom;
    private String nom;
    private String motPasse;
    private String uriPhoto;
    private String courriel;
    private int numeroTelephone;
    private Monnaie monnaieUsuelle;

    public Utilisateur(String prenom, String nom, String courriel) {
        this.prenom = prenom;
        this.nom = nom;
        this.courriel = courriel;
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

}
