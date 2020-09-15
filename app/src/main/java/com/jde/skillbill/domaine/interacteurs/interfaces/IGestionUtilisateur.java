package com.jde.skillbill.domaine.interacteurs.interfaces;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Utilisateur;

public interface IGestionUtilisateur {

    boolean creerUtilisateur(String nom, String prenom, String courriel);
    boolean modifierMotPasse(Utilisateur utilisateur, String motPasse);
    boolean ajouterPhotoProfil(Utilisateur utilisateur,Bitmap bitmap);
    boolean modifierPhotoProfil(Utilisateur utilisateur, Bitmap bitmap);
    boolean modifierNumeroTelephone(Utilisateur utilisateur, int numeroTelephone);
}
