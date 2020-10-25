package com.jde.skillbill.donnees.APIRest.entites;

import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

public class UtilisateurRestAPI extends Utilisateur {

    int id;
    String mot_de_passe;

    public UtilisateurRestAPI(String nom, String courriel, String motPasse, Monnaie monnaie, int id) {
        super(nom, courriel, motPasse, monnaie);
        this.id = id;
        mot_de_passe = motPasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

  
}
