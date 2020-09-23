package com.jde.skillbill.presentation.modele;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;


public class Modele {

    private DAO<Utilisateur> utilisateurDAO;
    private List<DAO<Groupe>> groupeAbonnes;
    private DAOFactory<Utilisateur,Groupe> utilisateurGroupeDAOFactory;

    //A enlever
    public Modele(DAOFactory<Utilisateur,Groupe> utilisateurGroupeDAOFactory) {
        groupeAbonnes= utilisateurGroupeDAOFactory.lirePar(utilisateurDAO);
        this.utilisateurGroupeDAOFactory=utilisateurGroupeDAOFactory;
    }

    public Modele() {

    }

    public boolean utilisateurExistant(String email){return true;}

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurDAO.lire();
    }

    public  void setUtilisateurConnecte(DAO<Utilisateur> daoUtilistateur){
        this.utilisateurDAO= daoUtilistateur;
    }

    public void ajouterGroupe(Groupe groupeCree) {
        utilisateurGroupeDAOFactory.creerPar(utilisateurDAO, groupeCree);
        Log.d("ajouterGroupe(Groupe groupeCree)",utilisateurGroupeDAOFactory.lirePar(utilisateurDAO).get(0).lire().toString());
    }

}
