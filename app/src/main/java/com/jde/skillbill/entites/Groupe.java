package com.jde.skillbill.entites;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Groupe {

    private String nomGroupe;
    private Date dateCreation;
    private Set<Utilisateur> utilisateurs;

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Map<Utilisateur, Double> getSoldeParUtilisateur() {
        return soldeParUtilisateur;
    }

    public void setSoldeParUtilisateur(Map<Utilisateur, Double> soldeParUtilisateur) {
        this.soldeParUtilisateur = soldeParUtilisateur;
    }

    public Monnaie getMonnaieDuGroupe() {
        return monnaieDuGroupe;
    }

    public void setMonnaieDuGroupe(Monnaie monnaieDuGroupe) {
        this.monnaieDuGroupe = monnaieDuGroupe;
    }

    public Utilisateur getUtilisateurCreateurGroupe() {
        return utilisateurCreateurGroupe;
    }

    public void setUtilisateurCreateurGroupe(Utilisateur utilisateurCreateurGroupe) {
        this.utilisateurCreateurGroupe = utilisateurCreateurGroupe;
    }

    private Map<Utilisateur, Double> soldeParUtilisateur;
    private Monnaie monnaieDuGroupe;
    private Utilisateur utilisateurCreateurGroupe;


}
