package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.domaine.interacteurs.interfaces.SourceDonneeException;

import java.time.LocalDate;
import java.util.List;

public interface ISourceDonnee {

    List<Facture> lireFacturesParGroupe(Groupe groupe) throws SourceDonneeException;

    boolean ajouterFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre) throws SourceDonneeException;

    boolean utilisateurExiste(String email) throws SourceDonneeException ;
    Utilisateur creerUtilisateur(Utilisateur utilisateur)throws SourceDonneeException;
    Utilisateur tenterConnexion(String email, String mdp) throws SourceDonneeException;
    Groupe creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe)throws SourceDonneeException ;
    List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur)throws SourceDonneeException;
    List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe)throws SourceDonneeException;

    boolean ajouterMembre(Groupe groupe, Utilisateur utilisateur)throws SourceDonneeException;
    boolean modifierFacture(Facture facture) throws SourceDonneeException;

    boolean creerFacture(Facture facture) throws SourceDonneeException;
}
