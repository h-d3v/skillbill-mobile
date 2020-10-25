package com.jde.skillbill.presentation.modele;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.presentation.presenteur.PresenteurVoirGroupes;

import java.util.ArrayList;
import java.util.List;


public class Modele {

    List<String> soldeParPostion = new ArrayList<>();
    Utilisateur utilisateurConnecte;
   List<Groupe> groupesAbonnes;

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public  void setUtilisateurConnecte(Utilisateur utilisateur){
        this.utilisateurConnecte= utilisateur;

    }
    public List<String> getSoldeParPosition(){
        return soldeParPostion;
    }

    public List<Groupe> getListGroupeAbonneUtilisateurConnecte() {
        return groupesAbonnes;
    }

    public void setGroupesAbonnesUtilisateurConnecte(List<Groupe> groupesAbonnes) {
        this.groupesAbonnes = groupesAbonnes;
    }
}
