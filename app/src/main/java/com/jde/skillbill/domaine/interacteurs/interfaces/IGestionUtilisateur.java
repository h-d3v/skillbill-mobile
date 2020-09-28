package com.jde.skillbill.domaine.interacteurs.interfaces;

import android.graphics.Bitmap;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface IGestionUtilisateur {
    /**
     *
     * @param nom
     * @param courriel
     * @return Utilisateur l'utilisateur cree
     */
    Utilisateur creerUtilisateur(String nom, String courriel, String motPasse);

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

    List<Groupe> trouverGroupesAbonne(Utilisateur utilisateur);
}
