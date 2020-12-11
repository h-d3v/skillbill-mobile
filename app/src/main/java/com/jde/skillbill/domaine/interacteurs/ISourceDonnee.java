package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;

import java.time.LocalDate;
import java.util.List;

public interface ISourceDonnee {
//region Facture
    List<Facture> lireFacturesParGroupe(Groupe groupe) throws SourceDonneeException;
    Facture rechargerFacture(Facture facture) throws SourceDonneeException;
    List<byte[]> chargerPhotos(Facture factureEnCours) throws SourceDonneeException;
    boolean modifierFacture(Facture facture) throws SourceDonneeException;
    boolean creerFacture(Facture facture) throws SourceDonneeException;
//endregion

//region Groupe
    Groupe creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe)throws SourceDonneeException ;
    List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe)throws SourceDonneeException;
    boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur)throws SourceDonneeException;
    List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur)throws SourceDonneeException;
//endregion

    Utilisateur modifierUtilisateur(Utilisateur utilisateurModifier, Utilisateur utilisateurCourrant) throws SourceDonneeException, UtilisateurException;

//region Utilisateur
    Utilisateur modifierUtilisateur(Utilisateur utilisateurModifier, Utilisateur utilisateurCourrant) throws SourceDonneeException;
    boolean utilisateurExiste(String email) throws SourceDonneeException ;
    Utilisateur creerUtilisateur(Utilisateur utilisateur)throws SourceDonneeException;

    /**
     *
     * @param email
     * @param mdp
     * @return null si l'utilisateur ne peut pas se connecter selon son mdp et email entrés en param
     * SINON l'utilisateur tel qu'il est dans la source de données
     * @throws SourceDonneeException
     */
    Utilisateur tenterConnexion(String email, String mdp) throws SourceDonneeException;
//endregion






}
