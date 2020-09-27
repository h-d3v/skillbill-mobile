package com.jde.skillbill.presentation;

import com.jde.skillbill.presentation.presenteur.PresenteurConnexion;

public interface IContratVPConnexion {
    interface IPresenteurConnexion{
        boolean tenterConnexion(String email, String mdp);
        void allerInscription();
    }

    interface IVueConnexion{
        String getMdp();
        String getEmail();
        boolean verifierLesChamps();
        void setPresenteur(PresenteurConnexion presenteur);
    }

}
