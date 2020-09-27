package com.jde.skillbill.presentation;


import com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte;

public interface IContratVPCreerCompte {

    interface VueCreerCompte{
        void setPresenteur(com.jde.skillbill.presentation.presenteur.PresenteurCreerCompte presenteurCreerCompte);
        String getNom();
        String getEmail();
        String getPass();
        boolean verifierMDP();
        boolean verifierNom();
        boolean verifierEmail();
    }

    interface PresenteurCreerCompte{
       void creerCompte();
       void retourLogin();
    }
}
