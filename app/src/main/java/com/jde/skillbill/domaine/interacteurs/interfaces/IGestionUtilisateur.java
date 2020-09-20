package com.jde.skillbill.domaine.interacteurs.interfaces;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Utilisateur;

public interface IGestionUtilisateur {
    /**
     *
     * @param nom
     * @param prenom
     * @param courriel
     * @return Utilisateur l'utilisateur cree
     */
    Utilisateur creerUtilisateur(String nom, String prenom, String courriel, String motPasse);

    /**
     *
     * @param utilisateur
     * @param motPasse
     * @return boolen true si la modification est faite, false sinon
     */
    boolean modifierMotPasse(Utilisateur utilisateur, String motPasse);
    boolean ajouterPhotoProfil(Utilisateur utilisateur,Bitmap bitmap);
    boolean modifierPhotoProfil(Utilisateur utilisateur, Bitmap bitmap);
    boolean modifierNumeroTelephone(Utilisateur utilisateur, int numeroTelephone);
}
