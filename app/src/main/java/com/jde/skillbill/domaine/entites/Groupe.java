package com.jde.skillbill.domaine.entites;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Groupe {
    @SerializedName("Nom")
    private String nomGroupe;
    @SerializedName("DateCreation")
    private Date dateCreation;
    @SerializedName("UtilisateursAbonnes")
    private List<Utilisateur> utilisateurs;
    @SerializedName("factures")
    private List<Facture> factures;
    private Map<Utilisateur, Double> soldeParUtilisateur;
    private Monnaie monnaieDuGroupe;
    @SerializedName("UtilisateurCreateur")
    private Utilisateur utilisateurCreateurGroupe;

    public Groupe(String nomGroupe, Utilisateur utilisateurCreateurGroupe,  Monnaie monnaieDuGroupe) {
        this.nomGroupe = nomGroupe;
        this.monnaieDuGroupe = monnaieDuGroupe;
        this.utilisateurCreateurGroupe = utilisateurCreateurGroupe;
    }

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

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
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

    @Override
    public String toString() {
        return "Groupe{" +
                "nomGroupe='" + nomGroupe + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupe groupe = (Groupe) o;
        return Objects.equals(nomGroupe, groupe.nomGroupe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomGroupe);
    }
}
