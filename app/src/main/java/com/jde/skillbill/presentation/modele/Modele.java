package com.jde.skillbill.presentation.modele;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import java.util.List;


public class Modele {

   Utilisateur utilisateurConnecte;
   List<Groupe> groupesAbonnes;

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public  void setUtilisateurConnecte(Utilisateur utilisateur){
        this.utilisateurConnecte= utilisateur;

    }

    public List<Groupe> getListGroupeAbonneUtilisateurConnecte() {
        return groupesAbonnes;
    }

    public void setGroupesAbonnesUtilisateurConnecte(List<Groupe> groupesAbonnes) {
        this.groupesAbonnes = groupesAbonnes;
    }
}
