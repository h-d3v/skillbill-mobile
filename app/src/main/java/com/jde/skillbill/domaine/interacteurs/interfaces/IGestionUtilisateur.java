package com.jde.skillbill.domaine.interacteurs.interfaces;


import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface IGestionUtilisateur  {
    /**
     *
     * @param email
     * @return true si le courriel est dans la source de donnée
     */
    boolean utilisateurExiste(String email) throws SourceDonneeException;

    /**
     *
     * @param nom
     * @param courriel
     * @return Utilisateur l'utilisateur crée
     */
    Utilisateur creerUtilisateur(String nom, String courriel, String motPasse, Monnaie monnaie)throws SourceDonneeException;


    /**
     *
     * @param utilisateur
     * @return la liste des groupes auxquels l'utilisateur est inscrit
     */
    List<Groupe> trouverGroupesAbonne(Utilisateur utilisateur)throws SourceDonneeException;
}
