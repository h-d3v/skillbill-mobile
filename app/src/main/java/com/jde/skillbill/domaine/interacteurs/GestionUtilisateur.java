package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;
import com.jde.skillbill.donnees.mockDAO.SourceDonneesMock;

import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.List;

public class GestionUtilisateur  implements IGestionUtilisateur {
    /*
     * TODO unifier les interfaces
     */
    ISourceDonnee _dataSource;
    Utilisateur utilisateur=null;

    public GestionUtilisateur(ISourceDonnee sourceDonnee) {
        _dataSource=sourceDonnee;
    }

    /**
     * Accesseur de l'instance d'un Singleton
     */
    public void setSource(ISourceDonnee dataSource){
        assert dataSource!=null;
        _dataSource= dataSource;
    }

    //Pour verifier si l'email est pris
    @Override
    public boolean utilisateurExiste(String email) throws SourceDonneeException{

        return _dataSource.utilisateurExiste(email);
    }

    public Utilisateur tenterConnexion(String email, String mdp) throws SourceDonneeException {
        return _dataSource.tenterConnexion(email, mdp);
    }

    @Override
    public Utilisateur creerUtilisateur(String nom, String courriel, String motPasse, Monnaie monnaie) throws SourceDonneeException {
        return _dataSource.creerUtilisateur(new Utilisateur(nom, courriel, motPasse, monnaie));
    }


    @Override
    public List<Groupe> trouverGroupesAbonne(Utilisateur utilisateur) throws SourceDonneeException {
        return _dataSource.lireTousLesGroupesAbonnes(utilisateur) ;
    }
}
