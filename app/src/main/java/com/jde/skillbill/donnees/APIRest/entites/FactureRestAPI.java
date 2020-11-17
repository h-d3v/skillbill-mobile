package com.jde.skillbill.donnees.APIRest.entites;


import com.google.gson.annotations.SerializedName;
import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactureRestAPI extends Facture {
    @SerializedName("PayeursEtMontant")
    private List<PayeursEtMontant> payeursEtMontantsListe;
    @SerializedName("DateCreation")
    private String date;
    @SerializedName("Id")
    private int id;
    @SerializedName("MontantTotal")
    double montantTotal;
    @SerializedName("IdGroupe")
    int idGroupe;
    @SerializedName("UtilisateurCreateurId")
    int idUtilisateurCreateur;


    public FactureRestAPI(String date, int idGroupe, double montantTotal, int idUtilisateurCreateur){
        this.date=date;
        this.idGroupe=idGroupe;
        this.montantTotal= montantTotal;
        this.idUtilisateurCreateur = idUtilisateurCreateur;
    }

    public List<PayeursEtMontant> getPayeursEtMontantsListe() {
        return payeursEtMontantsListe;
    }

    public void setPayeursEtMontantsListe(List<PayeursEtMontant> payeursEtMontantsListe) {
        this.payeursEtMontantsListe = payeursEtMontantsListe;
        HashMap<Utilisateur, Double> utilisateurMontantMap = new HashMap<>();

            for (PayeursEtMontant payeursEtMontant :payeursEtMontantsListe) {
                utilisateurMontantMap.put(new UtilisateurRestAPI(payeursEtMontant.getIdPayeur()), payeursEtMontant.getMontantPaye());
            }
            super.setMontantPayeParParUtilisateur(utilisateurMontantMap);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double getMontantTotal(){
        return montantTotal;
    }


}
