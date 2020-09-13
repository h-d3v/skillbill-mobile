package com.jde.skillbill.entites;

import java.util.Date;
import java.util.Map;

public class Facture {
    Activite activite;
    Date dateFacture;
    String uriImageFacture;
    Map<Utilisateur, Double> soldeParUtilisateur;

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getUriImageFacture() {
        return uriImageFacture;
    }

    public void setUriImageFacture(String uriImageFacture) {
        this.uriImageFacture = uriImageFacture;
    }

    public Map<Utilisateur, Double> getSoldeParUtilisateur() {
        return soldeParUtilisateur;
    }

    public void setSoldeParUtilisateur(Map<Utilisateur, Double> soldeParUtilisateur) {
        this.soldeParUtilisateur = soldeParUtilisateur;
    }
}
