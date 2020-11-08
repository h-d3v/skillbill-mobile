package com.jde.skillbill.donnees.APIRest.entites;

import com.google.gson.annotations.SerializedName;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

public class UtilisateurRestAPI extends Utilisateur {
    @SerializedName("Id")
    int id;


    public UtilisateurRestAPI(String nom, String courriel, String motPasse, Monnaie monnaie, int id) {
        super(nom, courriel, motPasse, monnaie);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


  
}
