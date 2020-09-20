package com.jde.skillbill.presentation.modele;

import android.util.Log;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Modele {

    private DAO<Utilisateur> utilisateurDAO;
    private List<DAO<Groupe>> groupeAbonnes;
    private DAOFactory<Utilisateur,Groupe> utilisateurGroupeDAOFactory;

    public Modele(DAOFactory<Utilisateur,Groupe> utilisateurGroupeDAOFactory) {
        groupeAbonnes= utilisateurGroupeDAOFactory.lirePar(utilisateurDAO);
        this.utilisateurGroupeDAOFactory=utilisateurGroupeDAOFactory;
    }

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

    public List<Groupe> getListGroupeAbonneUtilisateurConnecte() {
        Iterator<DAO<Groupe>> iterator = utilisateurGroupeDAOFactory.lirePar(utilisateurDAO).iterator();
        List<Groupe> groupes= new ArrayList<>();
        while (iterator.hasNext()){
            groupes.add(iterator.next().lire());
        }
        return groupes;
    }
}
