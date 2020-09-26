package com.jde.skillbill.domaine.interacteurs;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.IGestionUtilisateur;

import java.util.List;

public class GestionUtilisateur implements IGestionUtilisateur {
    ISourceDonnee iSourceDonnee;
    public GestionUtilisateur(ISourceDonnee iSourceDonnee){
        this.iSourceDonnee=iSourceDonnee;
    }

    @Override
    public Utilisateur creerUtilisateur(String nom, String prenom, String courriel, String motPasse) {
        return new Utilisateur(prenom,nom,courriel, motPasse);
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
        return iSourceDonnee.lireTousLesGroupesAbonnes(utilisateur);
    }
}
