package com.jde.skillbill.presentation.modele;

import android.app.Activity;
import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.ArrayList;
import java.util.List;


public class Modele {
    Utilisateur utilisateurConnecte= new Utilisateur("","test",null, null);
    List<Groupe> groupeAbonnes = new ArrayList<>();

    public Modele(Activity Activity) {
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public List<Groupe> getGroupeAbonnes() {
        return groupeAbonnes;
    }



    public void ajouterGroupe(Groupe groupeCree) {
        groupeAbonnes.add(groupeCree);
    }

}
