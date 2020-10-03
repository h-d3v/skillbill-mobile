package com.jde.skillbill.domaine.entites;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Facture {
    private  LocalDate dateFacture;
    private  String uriImageFacture;
    private  Map<Utilisateur, Double> montantPayeParParUtilisateur;
    private String libelle;


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

    public Map<Utilisateur, Double> getMontantPayeParParUtilisateur(Set<Utilisateur> utilisateurs) {
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
}
