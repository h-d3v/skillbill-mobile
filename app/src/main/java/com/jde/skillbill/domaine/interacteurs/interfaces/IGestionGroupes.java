package com.jde.skillbill.domaine.interacteurs.interfaces;

import com.jde.skillbill.domaine.entites.*;

import java.util.List;
import java.util.Set;

public interface IGestionGroupes {

    Groupe creerGroupe(String nom, Utilisateur utilisateurCreateur, Monnaie monnaieAUtiliserDansLeGroupe);
    Utilisateur ajouterMembre(Groupe groupe, Utilisateur utilisateur);
    boolean supprimerMembre(Groupe groupe,Utilisateur utilisateur);
    boolean ajouterFacture(Groupe groupe, Facture facture);
    List<Facture> trouverToutesLesFactures(Groupe groupe);
    Facture trouverUneFaxcture(Groupe groupe, Facture facture);
    Set<Utilisateur> trouverTousLesUtilisateurs(Groupe groupe);
    Double soldeUtilisateur(Groupe groupe,Utilisateur utilisateur);
    boolean modifierNomGroupe(Groupe groupe,String unNom);
    boolean payerUnUtilisateur(Groupe groupe,Paiement paiement);

}
