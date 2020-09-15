package com.jde.skillbill.domaine.entites;

import java.util.Date;
import java.util.Map;

public class Facture {
    Date dateFacture;
    String uriImageFacture;
    Map<Utilisateur, Double> montantPayeParParUtilisateur;


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

    public Map<Utilisateur, Double> getMontantPayeParParUtilisateur() {
        return montantPayeParParUtilisateur;
    }

    public void setMontantPayeParParUtilisateur(Map<Utilisateur, Double> soldeParUtilisateur) {
        this.montantPayeParParUtilisateur = soldeParUtilisateur;
    }
}
