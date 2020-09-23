package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;
import android.util.Log;

import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;

public class GestionUtilisateur implements IGestionUtilisateur {

    DataSourceUsers _dataSource;
    Utilisateur utilisateur=null;

    public GestionUtilisateur(DataSourceUsers dataSource) {
    }

    /**
     * Accesseur de l'instance d'un Singleton
     */
    public void setSource(DataSourceUsers dataSource){
        assert dataSource!=null;
        _dataSource = dataSource;
    }

    public boolean utilisateurExiste(String email){
        Log.i("String email", email);
        utilisateur=_dataSource.lire(email);
        return utilisateur != null;
    }


    @Override
    public Utilisateur creerUtilisateur(String nom, String courriel, String motPasse) {
        return _dataSource.creerUtilisateur(new Utilisateur(nom, courriel, motPasse));
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
}
