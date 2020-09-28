package com.jde.skillbill.domaine.interacteurs;

import com.jde.skillbill.domaine.entites.Utilisateur;

public interface DataSourceUsers {
    Utilisateur lire(String email);
    Utilisateur creerUtilisateur(Utilisateur utilisateur);
    Utilisateur tenterConnexion(String email, String mdp);

}
