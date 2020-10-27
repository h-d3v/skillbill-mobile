package com.jde.skillbill.presentation;


import com.jde.skillbill.domaine.entites.Monnaie;
import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;

public interface IContratVPCreerCompte {

    interface VueCreerCompte{
        void setPresenteur(com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte presenteurCreerCompte);
        String getNom();
        String getEmail();
        String getPass();
        String getPassVerif();

        Monnaie getMonnaieChoisie();
        boolean tousLesChampsValides();
        void afficherCompteCreer(String nom, String email, Monnaie monnaie);
        void afficherEmailDejaPrit();
    }

    interface PresenteurCreerCompte{
       void creerCompte();
       void retourLogin();
    }
}
