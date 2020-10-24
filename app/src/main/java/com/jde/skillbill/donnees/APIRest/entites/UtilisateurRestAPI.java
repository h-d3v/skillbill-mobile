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
}
