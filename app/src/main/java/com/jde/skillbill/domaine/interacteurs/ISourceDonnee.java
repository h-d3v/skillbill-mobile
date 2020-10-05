package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Facture;
import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public interface ISourceDonnee {

    List<Facture> lireFacturesParGroupe(Groupe groupe);

    boolean ajouterFacture(double montantTotal, Utilisateur utilisateurPayeur, LocalDate localDate, Groupe groupe, String titre);

    Utilisateur lireUtilisateur(String email);
    Utilisateur creerUtilisateur(Utilisateur utilisateur);
    Utilisateur tenterConnexion(String email, String mdp);
    boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) ;
    List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur);
    List<Utilisateur> lireUTilisateurParGroupe(Groupe groupe);

}
