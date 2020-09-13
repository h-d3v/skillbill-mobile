package com.jde.skillbill.entites;

import java.util.Date;
import java.util.List;

public class Paiement {
    private Groupe groupe;
    private Utilisateur utilisateurPayeur;
    private List<Facture> factures;
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

    public List<Facture> getFacture() {
        return factures;
    }

    public void setFacture(List<Facture> factures) {
        this.factures = factures;
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
