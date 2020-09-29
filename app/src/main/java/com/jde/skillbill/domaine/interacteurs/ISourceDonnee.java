package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface ISourceDonnee {
    Utilisateur lireUtilisateur(String email);
    Utilisateur creerUtilisateur(Utilisateur utilisateur);
    Utilisateur tenterConnexion(String email, String mdp);
    boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) ;
    List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur);
}
