package com.jde.skillbill.domaine.interacteurs.interfaces;

import com.jde.skillbill.domaine.entites.*;

import java.util.List;

public interface IGestionGroupes {
    /**
     *
     * @param nom
     * @param utilisateurCreateur
     * @param monnaieAUtiliserDansLeGroupe
     * @return le groupe crée
     */
    Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe);

    /**
     *
     * @param groupe
     * @param utilisateur
     * @return le membre ajouté
     */
    boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur);

    /**
     *
     * @param groupe
     * @param utilisateur
     * @return le membre supprimé
     */
    boolean supprimerMembre(Groupe groupe,Utilisateur utilisateur);

    /**
     *
     * @param groupe
     * @param facture
     * @return la facture ajoutéee
     */
    Facture ajouterFacture(Groupe groupe, Facture facture);

    /**
     *
     * @param groupe
     * @return toutes les factures du groupe
     */
    List<Facture> trouverToutesLesFactures(Groupe groupe);

    /**
     *
     * @param groupe
     * @param facture
     * @return la facture touvée, null sinon
     */
    Facture trouverUneFacture(Groupe groupe, Facture facture);

    /**
     *
     * @param groupe
     * @return tous les utilisateurs du groupe
     */
    List<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe);

    /**
     *
     * @param groupe
     * @param utilisateur
     * @return le solde d'un utilisateur au sein du groupe
     */
    Double soldeUtilisateur(Groupe groupe,Utilisateur utilisateur);

    /**
     *
     * @param groupe
     * @param unNom
     * @return le groupe modifié
     */
    Groupe modifierNomGroupe(Groupe groupe,String unNom);

    /**
     *
     * @param groupe
     * @param paiement
     */
    void payerUnUtilisateurDansSonGroupe(Groupe groupe, Paiement paiement);

    double getSoldeParUtilisateurEtGroupe(Utilisateur utilisateurConnecte, Groupe groupe);
}
