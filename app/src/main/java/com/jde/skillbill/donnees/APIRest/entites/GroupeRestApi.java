package com.jde.skillbill.donnees.APIRest.entites;

import com.google.gson.annotations.SerializedName;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;


public class GroupeRestApi extends Groupe {

    @SerializedName("Id")
    int id;
    @SerializedName("UtilisateursAbonnes")
    List<UtilisateurRestAPI> utilisateursRestApi;
    @SerializedName("DateCreation")
    String date;



    public GroupeRestApi(String nomGroupe, Utilisateur utilisateurCreateurGroupe, Monnaie monnaieDuGroupe, int id) {
        super(nomGroupe, utilisateurCreateurGroupe, monnaieDuGroupe);
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<UtilisateurRestAPI> getUtilisateursRestApi() {
        return utilisateursRestApi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
