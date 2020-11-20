package com.jde.skillbill.donnees.APIRest.entites;

import com.google.gson.annotations.SerializedName;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.Objects;

public class UtilisateurRestAPI extends Utilisateur {
    @SerializedName("Id")
    int id;


    public UtilisateurRestAPI(String nom, String courriel, String motPasse, Monnaie monnaie, int id) {
        super(nom, courriel, motPasse, monnaie);
        this.id = id;
    }

    public UtilisateurRestAPI(int idPayeur) {
        id=idPayeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
