package com.jde.skillbill.domaine.entites;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Facture implements Serializable {

    private LocalDate dateFacture;
    private  String uriImageFacture;
    private  Map<Utilisateur, Double> montantPayeParParUtilisateur;
    @SerializedName("Nom")
    private String libelle;
    @SerializedName("MontantTotal")
    private double montantTotal;
    private Groupe groupe;
    private Utilisateur utilisateurCreateur;
    List<byte[]> photos;


    //Constructeur de test
    public Facture(LocalDate dateFacture, Map<Utilisateur, Double> utilisateurDoubleMap, String nom){
        this.dateFacture=dateFacture;
        this.montantPayeParParUtilisateur=utilisateurDoubleMap;
        this.libelle=nom;
        photos = new ArrayList<>();
    }

    public Facture(){
        photos = new ArrayList<>();
    };

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getUriImageFacture() {
        return uriImageFacture;
    }

    public void setUriImageFacture(String uriImageFacture) {
        this.uriImageFacture = uriImageFacture;
    }

    public Map<Utilisateur, Double> getMontantPayeParParUtilisateur() {
        return montantPayeParParUtilisateur;
    }

    public void setMontantPayeParParUtilisateur(Map<Utilisateur, Double> soldeParUtilisateur) {
        this.montantPayeParParUtilisateur = soldeParUtilisateur;

    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public double calculerMontantTotalParUtilisateur(){
        double somme=0;
        for(Utilisateur utilisateur : montantPayeParParUtilisateur.keySet()){
            somme+=montantPayeParParUtilisateur.get(utilisateur);
        }
        return somme;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public List<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<byte[]> photos) {
        this.photos = photos;
    }

    public Object getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Utilisateur getUtilisateurCreateur() {
        return utilisateurCreateur;
    }

    public void setUtilisateurCreateur(Utilisateur utilisateurCreateur) {
        this.utilisateurCreateur = utilisateurCreateur;
    }
}
