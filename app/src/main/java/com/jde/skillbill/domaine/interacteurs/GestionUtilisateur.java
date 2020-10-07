package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;

import java.util.List;

public class GestionUtilisateur implements IGestionUtilisateur {
    /**
     * TODO unifier les interfaces
     */
    SourceDonneesMock _dataSource;
    Utilisateur utilisateur=null;

    public GestionUtilisateur(SourceDonneesMock sourceDonnee) {
        _dataSource=sourceDonnee;
    }


    /**
     * Accesseur de l'instance d'un Singleton
     */
    public void setSource(SourceDonneesMock dataSource){
        assert dataSource!=null;
        _dataSource= dataSource;
    }

    //Pour verifier si l'email est pris
    public boolean utilisateurExiste(String email){
        utilisateur=_dataSource.lireUtilisateur(email);
        return utilisateur != null;
    }

    public Utilisateur tenterConnexion(String email, String mdp){
        return _dataSource.tenterConnexion(email, mdp);
    }


    @Override
    public Utilisateur creerUtilisateur(String nom, String courriel, String motPasse, Monnaie monnaie) {
        return _dataSource.creerUtilisateur(new Utilisateur(nom, courriel, motPasse, monnaie));
    }

    @Override
    public boolean modifierMotPasse(Utilisateur utilisateur, String motPasse) {
        return false;
    }

    @Override
    public boolean ajouterPhotoProfil(Utilisateur utilisateur, Bitmap bitmap) {
        return false;
    }

    @Override
    public boolean modifierPhotoProfil(Utilisateur utilisateur, Bitmap bitmap) {
        return false;
    }

    @Override
    public boolean modifierNumeroTelephone(Utilisateur utilisateur, int numeroTelephone) {
        return false;
    }

    @Override
    public List<Groupe> trouverGroupesAbonne(Utilisateur utilisateur) {
        return _dataSource.lireTousLesGroupesAbonnes(utilisateur) ;
    }
}
