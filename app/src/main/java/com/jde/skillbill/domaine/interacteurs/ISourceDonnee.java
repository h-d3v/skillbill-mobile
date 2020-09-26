package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Groupe;
import com.jde.skillbill.domaine.entites.Utilisateur;

import java.util.List;

public interface ISourceDonnee {
     boolean creerGroupeParUtilisateur(Utilisateur utilisateur, Groupe groupe) ;
    List<Groupe> lireTousLesGroupesAbonnes(Utilisateur utilisateur);
}
