package com.jde.skillbill.entites;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Activite {

    private Groupe groupeAffilie;
    private Date dateDebut;

    public Groupe getGroupeAffilie() {
        return groupeAffilie;
    }

    public void setGroupeAffilie(Groupe groupeAffilie) {
        this.groupeAffilie = groupeAffilie;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Map<Utilisateur, Double> getSoldeParUtilisateur() {
        return soldeParUtilisateur;
    }

    public void setSoldeParUtilisateur(Map<Utilisateur, Double> soldeParUtilisateur) {
        this.soldeParUtilisateur = soldeParUtilisateur;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    private Date dateFin;
    private Map<Utilisateur, Double> soldeParUtilisateur;
    private List<Facture> factures;


}
