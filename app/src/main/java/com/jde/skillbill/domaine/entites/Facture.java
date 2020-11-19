package com.jde.skillbill.domaine.entites;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;


public class Facture implements Serializable {

    private  LocalDate dateFacture;
    private  String uriImageFacture;
    private  Map<Utilisateur, Double> montantPayeParParUtilisateur;
    @SerializedName("Nom")
    private String libelle;
    @SerializedName("MontantTotal")
    private double montantTotal;

    //Constructeur de test
    public Facture(LocalDate dateFacture, Map<Utilisateur, Double> utilisateurDoubleMap, String nom){
        this.dateFacture=dateFacture;
        this.montantPayeParParUtilisateur=utilisateurDoubleMap;
        this.libelle=nom;
    }

    public Facture(){};

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
}
