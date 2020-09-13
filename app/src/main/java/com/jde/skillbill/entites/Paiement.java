package com.jde.skillbill.entites;

import java.util.Date;

public class Paiement {
    private Groupe groupe;
    private Utilisateur utilisateurPayeur;
    private Facture facture;
    private Utilisateur utilisateurPaye;
    private Date datePaiement;

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Utilisateur getUtilisateurPayeur() {
        return utilisateurPayeur;
    }

    public void setUtilisateurPayeur(Utilisateur utilisateurPayeur) {
        this.utilisateurPayeur = utilisateurPayeur;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Utilisateur getUtilisateurPaye() {
        return utilisateurPaye;
    }

    public void setUtilisateurPaye(Utilisateur utilisateurPaye) {
        this.utilisateurPaye = utilisateurPaye;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }
}
