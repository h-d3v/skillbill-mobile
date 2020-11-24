package com.jde.skillbill.domaine.interacteurs.interfaces;

import com.jde.skillbill.domaine.entites.*;

import java.util.List;

public interface IGestionGroupes  {
    /**
     *
     * @param nom
     * @param utilisateurCreateur
     * @param monnaieAUtiliserDansLeGroupe
     * @return le groupe crée
     */
    Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe)throws SourceDonneeException;

    /**
     *
     * @param groupe
     * @param utilisateur
     * @return le membre ajouté
     */
    boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur)throws SourceDonneeException;



    /**
     *
     * @param groupe
     * @param facture
     * @return la facture ajoutéee
     */
    Facture ajouterFacture(Groupe groupe, Facture facture)throws SourceDonneeException;

    /**
     *
     * @param groupe
     * @return toutes les factures du groupe
     */
    List<Facture> trouverToutesLesFactures(Groupe groupe)throws SourceDonneeException;



    /**
     *
     * @param groupe
     * @return tous les utilisateurs du groupe
     */
    List<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe)throws SourceDonneeException;






    double getSoldeParUtilisateurEtGroupe(Utilisateur utilisateurConnecte, Groupe groupe)throws SourceDonneeException;
}
