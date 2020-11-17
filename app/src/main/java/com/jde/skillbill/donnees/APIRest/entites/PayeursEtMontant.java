package com.jde.skillbill.donnees.APIRest.entites;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayeursEtMontant implements Serializable {
    @SerializedName("UtilisateurId")
    private int idPayeur;
    @SerializedName("MontantPaye")
    private double montantPaye;
    public PayeursEtMontant(int idPayeur, double montantPaye){
        this.idPayeur=idPayeur;
        this.montantPaye=montantPaye;
    }

    public int getIdPayeur() {
        return idPayeur;
    }

    public void setIdPayeur(int idPayeur) {
        this.idPayeur = idPayeur;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(double montantPaye) {
        this.montantPaye = montantPaye;
    }
}
