package com.jde.skillbill.presentation;

import com.jde.skillbill.domaine.entites.Utilisateur;
import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;

public interface IContratVPConnexion {
    interface IPresenteurConnexion{
        void tenterConnexion(String email, String mdp);
        void allerInscription();
    }

    interface IVueConnexion{
        String getMdp();
        String getEmail();
        boolean tousLesChampsValides();
        void setPresenteur(PresenteurConnexion presenteur);

        void fermerProgressBar();

        void ouvrirProgressBar();
    }

}
